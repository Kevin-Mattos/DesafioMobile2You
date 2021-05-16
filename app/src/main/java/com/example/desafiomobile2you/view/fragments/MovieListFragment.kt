package com.example.desafiomobile2you.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.desafiomobile2you.MainActivity
import com.example.desafiomobile2you.databinding.FragmentMovieListBinding
import com.example.desafiomobile2you.repository.MovieRepository
import com.example.desafiomobile2you.view.adapters.MovieAdapter
import com.example.desafiomobile2you.view.viewModels.MovieListFragmentViewModel
import com.example.desafiomobile2you.view.viewModels.factories.MovieListFragmentViewModelFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val MOVIE_ID_TAG = "get_movie_id"
class MovieListFragment : Fragment(), MovieAdapter.MovieAction {

    private val mMainActivity: MainActivity by lazy {
        activity as MainActivity
    }

    private val mViewModel: MovieListFragmentViewModel by lazy {

        val retrofit =
            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.themoviedb.org/3/")
                .build()

        val factory = MovieListFragmentViewModelFactory(mMainActivity.application, MovieRepository(retrofit))
        ViewModelProvider(this, factory).get(MovieListFragmentViewModel::class.java)
    }

    private val adapter by lazy{
        mMainActivity.applicationContext?.let {
            MovieAdapter(it, actions = this)
        } ?: throw IllegalArgumentException("contexto invalido")

    }

    lateinit var mBinding: FragmentMovieListBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mViewModel.selectedMovieId = it.getInt(MOVIE_ID_TAG)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentMovieListBinding.inflate(inflater, container, false)
        setupAdapter()
        fetchSimilarMovies()

        return mBinding.root
    }



    private fun fetchSimilarMovies() {
        mViewModel.fetchSimilarMovies().observe(this, Observer {
            if(it.success){
                adapter.update(it.data!!.results)
            }
        })
    }

    private fun setupAdapter() {
        mBinding.myRecyclerView.adapter = adapter
    }


    override fun getMovieImageUrl(backdropPath: String, size: String): String = mViewModel.createImageUrl(backdropPath, size)

    override fun getMovieGenreById(genreIds: List<Long>): String {
       return mMainActivity.getMovieGenreById(genreIds)
    }


}