package com.example.desafiomobile2you

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.desafiomobile2you.databinding.ActivityMainBinding
import com.example.desafiomobile2you.view.adapters.MovieAdapter
import com.example.desafiomobile2you.view.viewModels.MainActivityViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), MovieAdapter.MovieAction {

    private val TAG = this.javaClass.canonicalName

    private val mBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val mViewModel: MainActivityViewModel by lazy {
        ViewModelProvider.AndroidViewModelFactory(application).create(
            MainActivityViewModel::class.java
        )
    }

    private val adapter by lazy{
        applicationContext?.let {
            MovieAdapter(it, actions = this)
        } ?: throw IllegalArgumentException("contexto invalido")

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        setSupportActionBar(mBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
        setupBinding()
        setupAdapter()

        mViewModel.movieGenres.observe(this, Observer { genreResource ->
            Log.d(TAG, "${genreResource.success}")
            if (genreResource.success) {
                observeImage()
                mViewModel.fetchDetails(550)
                mViewModel.fetchSimilarMovies(550).observe(this, Observer {
                    if(it.success){
                        adapter.update(it.data!!.results)
                    }
                })
            }
        })

    }

    private fun observeImage() {
        mViewModel.selectedMovie.observe(this, Observer {
            it?.let{
                Glide.with(this).load(getMovieImageUrl(it.posterPath, "w500")).into(mBinding.expandedImage)
            }
        })
    }

    private fun setupBinding() {
        mBinding.viewModel = mViewModel
        mBinding.lifecycleOwner = this
    }

    private fun setupAdapter() {
        mBinding.myRecyclerView.adapter = adapter
    }

    override fun getMovieGenreById(genreIds: List<Long>): String {
        return mViewModel.getMovieGenreById(genreIds)!!.joinToString { "${it.name}" }
    }

    override fun getMovieImageUrl(backdropPath: String, size: String): String {
        val path = mViewModel.createImageUrl(backdropPath, size)
        Log.d(TAG, "poster size: $size\npath: $path")
        return path
    }
}