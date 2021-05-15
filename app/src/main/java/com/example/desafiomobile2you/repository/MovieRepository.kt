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
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.RuntimeException
import java.net.ConnectException
import java.net.UnknownHostException

class MovieRepository(retrofit: Retrofit) {

    private val TAG = this.javaClass.name

    var apiKey: String? = "c108085f50217432acf2932b479570a7"

    val movieApi: MovieApi = MovieApi(retrofit)

    fun fetchDetails(movieId: Int, callback: (Movie) -> Unit): LiveData<Resource<Movie, Exception>> {
        val liveData = MutableLiveData<Resource<Movie, Exception>>()
        apiKey?.let {
            CoroutineScope(Dispatchers.IO).launch {
                Log.d(TAG, "FETCHING DETAILS")
                val response = movieApi.fetchDetails(it, movieId)
                withContext(Dispatchers.Main) {
                   postResourse(liveData, response, callback)
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
                    postResourse(liveData, response)
                }
            }
        }
        return liveData
    }

    fun fetchMovieGenres(): LiveData<Resource<Genres, Exception>> {
        val liveData = MutableLiveData<Resource<Genres, Exception>>()
        apiKey?.let {
            CoroutineScope(Dispatchers.IO).launch {
                var response: Response<Genres?>? = movieApi.fetchMovieGenres(it)
                withContext(Dispatchers.Main) {
                    postResourse(liveData, response)
                }
            }
        }
        return liveData
    }

    fun <T> postResourse(liveData: MutableLiveData<Resource<T, Exception>>, response: Response<T?>?, callback: (T) -> Unit = {}) {
        if (response?.isSuccessful == true) {
            val result = response.body()!!
            liveData.postValue(Resource(result))
            callback(result)
        }else if (response?.isSuccessful == false) {
            Log.d(TAG,"Falhou\n ${response.body()}\n${response.errorBody()?.string()}")
            liveData.postValue(Resource(RuntimeException("Falha ao obter dados")))
        } else {
            liveData.postValue(Resource(ConnectException("Falha na conexão")))
        }
    }

}