package com.example.desafiomobile2you.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.desafiomobile2you.repository.api.MovieApi
import com.example.desafiomobile2you.repository.entities.Genres
import com.example.desafiomobile2you.repository.entities.Movie
import com.example.desafiomobile2you.repository.entities.SimilarMovies
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class MovieRepository(retrofit: Retrofit) {

    private val TAG = this.javaClass.canonicalName

    var apiKey: String? = "c108085f50217432acf2932b479570a7"

    val movieApi: MovieApi = MovieApi(retrofit)

    fun getDetails(movieId: Int): LiveData<Movie?> {
        val liveData = MutableLiveData<Movie?>()
        apiKey?.let {
            CoroutineScope(Dispatchers.IO).launch {
                val response = movieApi.getDetails(it, movieId)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful)
                        liveData.postValue(response.body())
                    else
                        Log.d(
                            TAG,
                            "Falhou\n ${response.body()}\n${response.errorBody()?.string()}"
                        )
                }
            }
        }

        return liveData

    }

    fun getSimilarMovies(id: Int): MutableLiveData<SimilarMovies> {

        val liveData = MutableLiveData<SimilarMovies>()
        apiKey?.let {
            CoroutineScope(Dispatchers.IO).launch {
                val response = movieApi.getSimilarMovies(it, id)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful)
                        liveData.postValue(response.body())
                    else
                        Log.d(
                            TAG,
                            "Falhou\n ${response.body()}\n${response.errorBody()?.string()}"
                        )
                }
            }
        }
        return liveData
    }

    fun getMovieGenres(): MutableLiveData<Genres> {

        val liveData = MutableLiveData<Genres>()
        apiKey?.let {
            CoroutineScope(Dispatchers.IO).launch {
                val response = movieApi.getMovieGenres(it)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful)
                        liveData.postValue(response.body())
                    else
                        Log.d(
                            TAG,
                            "Falhou\n ${response.body()}\n${response.errorBody()?.string()}"
                        )
                }
            }
        }
        return liveData
    }


}