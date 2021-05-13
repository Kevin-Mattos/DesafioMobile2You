package com.example.desafiomobile2you.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.desafiomobile2you.repository.api.MovieApi
import com.example.desafiomobile2you.repository.entities.Genres
import com.example.desafiomobile2you.repository.entities.Movie
import com.example.desafiomobile2you.repository.entities.SimilarMovies
import com.example.desafiomobile2you.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import java.lang.RuntimeException

class MovieRepository(retrofit: Retrofit) {

    private val TAG = this.javaClass.canonicalName

    var apiKey: String? = "c108085f50217432acf2932b479570a7"

    val movieApi: MovieApi = MovieApi(retrofit)

    fun fetchDetails(movieId: Int, callback: (Movie) -> Unit): LiveData<Resource<Movie, Exception>> {
        val liveData = MutableLiveData<Resource<Movie, Exception>>()
        apiKey?.let {
            CoroutineScope(Dispatchers.IO).launch {
                val response = movieApi.fetchDetails(it, movieId)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val result = response.body()
                        liveData.postValue(Resource(result))
                        callback(result)
                    }else {
                        Log.d(TAG,"Falhou\n ${response.body()}\n${response.errorBody()?.string()}")
                        liveData.postValue(Resource(RuntimeException("Falha ao obter generos")))
                    }
                }
            }
        }
        return liveData

    }

    fun fetchSimilarMovies(id: Int): LiveData<Resource<SimilarMovies, Exception>> {
        val liveData = MutableLiveData<Resource<SimilarMovies, Exception>>()
        apiKey?.let {
            CoroutineScope(Dispatchers.IO).launch {
                val response = movieApi.fetchSimilarMovies(it, id)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val result = response.body()
                        liveData.postValue(Resource(result))
                    }else {
                        Log.d(TAG,"Falhou\n ${response.body()}\n${response.errorBody()?.string()}")
                        liveData.postValue(Resource(RuntimeException("Falha ao filmes similares")))
                    }
                }
            }
        }
        return liveData
    }

    fun fetchMovieGenres(): LiveData<Resource<Genres, Exception>> {
        val liveData = MutableLiveData<Resource<Genres, Exception>>()
        apiKey?.let {
            CoroutineScope(Dispatchers.IO).launch {
                val response = movieApi.fetchMovieGenres(it)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val result = response.body()
                        liveData.postValue(Resource(result))
                    }else {
                        Log.d(TAG,"Falhou\n ${response.body()}\n${response.errorBody()?.string()}")
                        liveData.postValue(Resource(RuntimeException("Falha ao obter generos")))
                    }
                }
            }
        }
        return liveData
    }
}