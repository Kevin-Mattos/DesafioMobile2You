package com.example.desafiomobile2you

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.desafiomobile2you.databinding.ActivityMainBinding
import com.example.desafiomobile2you.repository.MovieRepository
import com.example.desafiomobile2you.view.extensions.transactionFragment
import com.example.desafiomobile2you.view.fragments.FailedToLoadMovieFragment
import com.example.desafiomobile2you.view.fragments.MOVIE_ID_TAG
import com.example.desafiomobile2you.view.fragments.MovieListFragment
import com.example.desafiomobile2you.view.viewModels.MainActivityViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

class MainActivity : AppCompatActivity() {

    private val TAG = this.javaClass.name

    private val mBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val mViewModel: MainActivityViewModel by lazy {
        ViewModelProvider(this).get(MainActivityViewModel::class.java)
    }

    val movieId = 10567

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        setSupportActionBar(mBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        setupBinding()
        loadMovie()
        observeImage()

    }

    private fun loadMovie(failedAgain: (() -> Unit)? = null) {
        mViewModel.movieGenres.removeObservers(this)
        mViewModel.movieGenres.observe(this, Observer { genreResource ->
            if (genreResource.success) {
                mViewModel.fetchDetails(movieId)
                showMovieListFragment()
            } else {
                Log.d(TAG, "falha ao obter generos: ${genreResource.error}")
                if(failedAgain == null)
                    showRetryFragment()
                else
                    failedAgain()
            }
        })

    }

    private fun showMovieListFragment() {
        val fragment = MovieListFragment()
        with(fragment) {
            arguments = Bundle()
            arguments?.putInt(MOVIE_ID_TAG, movieId)
        }
        transactionFragment {
            replace(R.id.fragment_container, fragment)
        }
    }

    fun retryLoading() {
        mViewModel.fetchMovieGenres()
    }

    private fun showRetryFragment() {
        val fragment = FailedToLoadMovieFragment()
        fragment.observe = ::loadMovie
        fragment.retry = ::retryLoading
        transactionFragment {
            replace(R.id.fragment_container, fragment)
        }
    }

    private fun observeImage() {

        mViewModel.selectedMovie.observe(this, Observer {
            it?.let{
                Glide.with(this).load(mViewModel.createImageUrl(it.posterPath, "w500")).into(mBinding.expandedImage)
            }
        })
    }

    private fun setupBinding() {
        mBinding.viewModel = mViewModel
        mBinding.lifecycleOwner = this
    }

    fun getMovieGenreById(genreIds: List<Long>): String {
        return mViewModel.getMovieGenreById(genreIds)!!.joinToString { it.name }
    }

}