package com.example.desafiomobile2you.view.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.desafiomobile2you.repository.MovieRepository
import com.example.desafiomobile2you.repository.entities.SimilarMovies
import com.example.desafiomobile2you.util.Resource
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieListFragmentViewModel(application: Application): AndroidViewModel(application) {

    var selectedMovieId = 0

    val retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.themoviedb.org/3/")
            .build()
    val movieRepository by lazy {
        MovieRepository(retrofit)
    }

    fun fetchSimilarMovies(): LiveData<Resource<SimilarMovies, Exception>> = movieRepository.fetchSimilarMovies(selectedMovieId)


    fun createImageUrl (backDropPath: String, size: String): String = "https://image.tmdb.org/t/p/$size/$backDropPath"


}