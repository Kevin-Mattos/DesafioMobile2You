package com.example.desafiomobile2you.repository.api

import android.util.Log
import com.example.desafiomobile2you.repository.entities.Genres
import com.example.desafiomobile2you.repository.entities.Movie
import com.example.desafiomobile2you.repository.entities.SimilarMovies
import com.example.desafiomobile2you.repository.services.MovieService
import retrofit2.Response
import retrofit2.Retrofit

class MovieApi (retrofit: Retrofit) {
    private val TAG = this.javaClass.canonicalName

    val movieService = retrofit.create(MovieService::class.java)

    fun fetchDetails(apiKey: String, id: Int) : Response<Movie?> {
        val call = movieService.getDetails(id, apiKey)
        Log.d(TAG, "${call.request().url()}")
        return call.execute()
    }

    fun fetchSimilarMovies(apiKey: String, id: Int): Response<SimilarMovies?> {

        val call = movieService.getSimilarMovies(id, apiKey)
        Log.d(TAG, "${call.request().url()}")
        return call.execute()
    }

    fun fetchMovieGenres(apiKey: String): Response<Genres?> {
        val call = movieService.getMovieGenres(apiKey)
        Log.d(TAG, "${call.request().url()}")
        return call.execute()

    }


}