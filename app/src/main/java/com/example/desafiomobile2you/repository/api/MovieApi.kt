package com.example.desafiomobile2you.repository.api

import com.example.desafiomobile2you.repository.entities.Genres
import com.example.desafiomobile2you.repository.entities.Movie
import com.example.desafiomobile2you.repository.entities.SimilarMovies
import com.example.desafiomobile2you.repository.services.MovieService
import retrofit2.Response
import retrofit2.Retrofit
import java.util.*

class MovieApi(retrofit: Retrofit) {

    val movieService = retrofit.create(MovieService::class.java)

    fun fetchDetails(apiKey: String, id: Int): Response<Movie?>? {
        val call = movieService.getDetails(id, apiKey, Locale.getDefault().toLanguageTag())
        return try {
            call.execute()
        } catch (e: Exception) {
            null
        }
    }

    fun fetchSimilarMovies(apiKey: String, id: Int): Response<SimilarMovies?>? {
        val call = movieService.getSimilarMovies(id, apiKey, Locale.getDefault().toLanguageTag())
        return try {
            call.execute()
        } catch (e: Exception) {
            null
        }
    }

    fun fetchMovieGenres(apiKey: String): Response<Genres?>? {
        val call = movieService.getMovieGenres(apiKey, Locale.getDefault().toLanguageTag())
        return try {
            call.execute()
        } catch (e: Exception) {
            null
        }
    }


}