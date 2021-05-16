package com.example.desafiomobile2you.view.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.desafiomobile2you.repository.MovieRepository
import com.example.desafiomobile2you.repository.entities.SimilarMovies
import com.example.desafiomobile2you.util.Resource

class MovieListFragmentViewModel(application: Application, val movieRepository: MovieRepository): AndroidViewModel(application) {

    var selectedMovieId = 0

    fun fetchSimilarMovies(): LiveData<Resource<SimilarMovies, Exception>> = movieRepository.fetchSimilarMovies(selectedMovieId)

    fun createImageUrl (backDropPath: String, size: String): String = "https://image.tmdb.org/t/p/$size/$backDropPath"

}