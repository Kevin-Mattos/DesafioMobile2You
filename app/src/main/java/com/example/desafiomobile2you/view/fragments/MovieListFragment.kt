package com.example.desafiomobile2you.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.desafiomobile2you.MainActivity
import com.example.desafiomobile2you.R
import com.example.desafiomobile2you.databinding.ActivityMainBinding
import com.example.desafiomobile2you.databinding.FragmentMovieListBinding
import com.example.desafiomobile2you.view.adapters.MovieAdapter
import com.example.desafiomobile2you.view.viewModels.MainActivityViewModel
import com.example.desafiomobile2you.view.viewModels.MovieListFragmentViewModel

const val MOVIE_ID_TAG = "get_movie_id"
class MovieListFragment : Fragment(), MovieAdapter.MovieAction {

    private val mMainActivity: MainActivity by lazy {
        activity as MainActivity
    }

    val TAG = this.javaClass.name

    private val mViewModel: MovieListFragmentViewModel by lazy {
        ViewModelProvider.AndroidViewModelFactory(mMainActivity.application).create(
            MovieListFragmentViewModel::class.java
        )
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

        return mBinding.root//inflater.inflate(R.layout.fragment_movie_list, container, false)
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


    override fun getMovieImageUrl(backdropPath: String, size: String): String {
        val path = mViewModel.createImageUrl(backdropPath, size)
        Log.d(TAG, "poster size: $size\npath: $path")
        return path
    }
    override fun getMovieGenreById(genreIds: List<Long>): String {
       return mMainActivity.getMovieGenreById(genreIds)
    }


}