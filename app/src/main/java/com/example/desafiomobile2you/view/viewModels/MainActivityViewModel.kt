package com.example.desafiomobile2you.view.viewModels

import android.app.Application
import android.util.Log
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
    val selectedMovie: MutableLiveData<Movie?> = MutableLiveData<Movie?>().apply { value = null }

    val selectedMovieLikes: MutableLiveData<String> = MutableLiveData<String>().apply { value = "0" }
    val selectedMoviePopularity: MutableLiveData<String> = MutableLiveData<String>().apply { value = "0" }

    val movieGenres: MutableLiveData<Resource<Genres, Exception>> = movieRepository.fetchMovieGenres()

    fun fetchDetails(movieId: Int) {
        movieRepository.fetchDetails(movieId) {
            if(it.success) {
                val movie = it.data!!
                selectedMovie.postValue(movie)
                selectedMovieLikes.postValue(withSuffix(movie.voteCount.toLong()))
                selectedMoviePopularity.postValue(withSuffix(movie.popularity.toLong()))
            }
        }
    }

    fun withSuffix(count: Long): String? {
        if (count < 1000) return "" + count
        val exp: Int = (Math.log(count.toDouble()) / Math.log(1000.0)).toInt()
        return String.format(
            "%.1f%c",
            count / Math.pow(1000.0, exp.toDouble()),
            "kMGTPE"[exp - 1]
        )
    }

    fun fetchMovieGenres() {
        movieRepository.fetchMovieGenres {
                movieGenres.postValue(it)
        }
    }

    fun createImageUrl (backDropPath: String, size: String): String = "https://image.tmdb.org/t/p/$size/$backDropPath"

    fun getMovieGenreById(genreIds: List<Long>): List<Genre>? = movieGenres.value?.data?.genres?.filter{genre ->  genreIds.any { id -> genre.id == id} }

}