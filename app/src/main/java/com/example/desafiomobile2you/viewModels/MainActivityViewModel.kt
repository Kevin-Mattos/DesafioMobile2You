package com.example.desafiomobile2you.viewModels

import android.app.Application
import android.util.Log
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.desafiomobile2you.repository.MovieRepository
import com.example.desafiomobile2you.repository.entities.Genre
import com.example.desafiomobile2you.repository.entities.Genres
import com.example.desafiomobile2you.repository.entities.Movie
import com.example.desafiomobile2you.repository.entities.SimilarMovies
import com.example.desafiomobile2you.util.Resource
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivityViewModel(application: Application): AndroidViewModel(application) {

    val retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.themoviedb.org/3/")
            .build()

    val movieRepository by lazy {
        MovieRepository(retrofit)
    }
    val movieName: MutableLiveData<String> = MutableLiveData<String>().apply { value = "Loading" }
    val movieUrl: MutableLiveData<String?> = MutableLiveData<String?>().apply { value = null }

    val movieGenres: LiveData<Resource<Genres, Exception>> =  movieRepository.fetchMovieGenres()

    fun fetchDetails(movieId: Int) {
        movieRepository.fetchDetails(movieId) {
            movieName.value = it.originalTitle
            movieUrl.value = createImageUrl(it.backdropPath)
        }
    }


    fun fetchSimilarMovies(movieId: Int): LiveData<Resource<SimilarMovies, Exception>> = movieRepository.fetchSimilarMovies(movieId)


    fun createImageUrl (backDropPath: String, size: String = "w500"): String = "https://image.tmdb.org/t/p/$size/$backDropPath"

    fun fetchMovieGenres(): LiveData<Resource<Genres, Exception>> = movieRepository.fetchMovieGenres()

    fun getMovieGenreById(genreIds: List<Long>): List<Genre>? = movieGenres.value?.data?.genres?.filter{genre ->  genreIds.any { id -> genre.id == id} }

}