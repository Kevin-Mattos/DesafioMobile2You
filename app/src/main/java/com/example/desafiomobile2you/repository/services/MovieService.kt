package com.example.desafiomobile2you.repository.services

import com.example.desafiomobile2you.repository.entities.Genres
import com.example.desafiomobile2you.repository.entities.Movie
import com.example.desafiomobile2you.repository.entities.SimilarMovies
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

private const val route = "movie"

interface MovieService {


    @Headers( "Content-Type: application/json;charset=UTF-8")
    @GET("$route/{id}")
    fun getDetails(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Call<Movie?>


    @Headers( "Content-Type: application/json;charset=UTF-8")
    @GET("$route/{id}/similar")
    fun getSimilarMovies(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Call<SimilarMovies?>

    @Headers( "Content-Type: application/json;charset=UTF-8")
    @GET("genre/$route/list")
    fun getMovieGenres(@Query("api_key") apiKey: String,@Query("language") language: String): Call<Genres?>


}