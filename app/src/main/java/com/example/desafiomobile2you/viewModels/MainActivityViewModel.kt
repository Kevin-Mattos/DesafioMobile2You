package com.example.desafiomobile2you.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.desafiomobile2you.repository.MovieRepository
import com.example.desafiomobile2you.repository.entities.Genres
import com.example.desafiomobile2you.repository.entities.Movie
import com.example.desafiomobile2you.repository.entities.SimilarMovies
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivityViewModel(application: Application): AndroidViewModel(application)  {

    val retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory
                .create()

            )
            .baseUrl("https://api.themoviedb.org/3/").build()

    val movieRepository by lazy {
        MovieRepository(retrofit)
    }

    fun getDetails(): LiveData<Movie?> {
        return movieRepository.getDetails(550)
    }

    fun getSimilarMovies(): MutableLiveData<SimilarMovies> {
        return movieRepository.getSimilarMovies(550)
    }

    fun getImageUrl (backDropPath: String, size: String = "w500"): String {
        return "https://image.tmdb.org/t/p/$size/$backDropPath"
    }

    fun getMovieGenres(): MutableLiveData<Genres> {
        return movieRepository.getMovieGenres()
    }

}