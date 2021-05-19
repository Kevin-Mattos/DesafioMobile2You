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
import com.example.desafiomobile2you.view.adapters.MovieAdapter
import com.example.desafiomobile2you.view.viewModels.MovieListFragmentViewModel

const val MOVIE_ID_TAG = "get_movie_id"
class MovieListFragment : Fragment(), MovieAdapter.MovieAction {

    private lateinit var mMainActivity: MainActivity

    private lateinit var mViewModel: MovieListFragmentViewModel

    private lateinit var adapter: MovieAdapter

    private lateinit var mBinding: FragmentMovieListBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupMainActivity()
        setupViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupBinding(inflater, container)
        setupAdapter()
        fetchSimilarMovies()
        return mBinding.root
    }

    override fun getMovieImageUrl(backdropPath: String, size: String): String = mViewModel.createImageUrl(backdropPath, size)

    override fun getMovieGenreById(genreIds: List<Long>): String {
        return mMainActivity.getMovieGenreById(genreIds)
    }

    private fun setupViewModel() {
        mViewModel = ViewModelProvider(this).get(MovieListFragmentViewModel::class.java)
        arguments?.let {
            mViewModel.selectedMovieId = it.getInt(MOVIE_ID_TAG, 0)
        }
    }

    private fun setupMainActivity() {
        mMainActivity = activity as MainActivity
    }

    private fun setupBinding(inflater: LayoutInflater, container: ViewGroup?) {
        mBinding = FragmentMovieListBinding.inflate(inflater, container, false)
    }

    private fun setupAdapter() {
        adapter =  mMainActivity.applicationContext?.let {
            MovieAdapter(it, actions = this)
        } ?: throw IllegalArgumentException("contexto invalido")
        mBinding.myRecyclerView.adapter = adapter
    }

    private fun fetchSimilarMovies() {
        mViewModel.fetchSimilarMovies().observe(viewLifecycleOwner, Observer {
            if(it.success){
                adapter.update(it.data!!.results)
            }
        })
    }

}