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
import java.net.ConnectException

class MovieRepository(retrofit: Retrofit) {

    private val TAG = this.javaClass.name

    var apiKey: String? = "c108085f50217432acf2932b479570a7"

    val movieApi: MovieApi = MovieApi(retrofit)

    fun fetchDetails(movieId: Int, callback: (Resource<Movie, Exception>) -> Unit): LiveData<Resource<Movie, Exception>> {
        val liveData = MutableLiveData<Resource<Movie, Exception>>()
        apiKey?.let {
            CoroutineScope(Dispatchers.IO).launch {
                Log.i(TAG, "obtendo detalhes do filme com id $movieId")
                val response = movieApi.fetchDetails(it, movieId)
                withContext(Dispatchers.Main) {
                   postResourse(liveData, response, callback)
                }
            }
        }
        return liveData
    }

    fun fetchSimilarMovies(id: Int, callback: (Resource<SimilarMovies, Exception>) -> Unit = {}): LiveData<Resource<SimilarMovies, Exception>> {
        val liveData = MutableLiveData<Resource<SimilarMovies, Exception>>()
        apiKey?.let {
            CoroutineScope(Dispatchers.IO).launch {
                Log.i(TAG, "obtendo filmes similares ao filme com id $id")
                val response = movieApi.fetchSimilarMovies(it, id)
                withContext(Dispatchers.Main) {
                    postResourse(liveData, response, callback)
                }
            }
        }
        return liveData
    }

    fun fetchMovieGenres(callback: (Resource<Genres, Exception>) -> Unit = {}): MutableLiveData<Resource<Genres, Exception>> {
        val liveData = MutableLiveData<Resource<Genres, Exception>>()
        apiKey?.let {
            CoroutineScope(Dispatchers.IO).launch {
                Log.i(TAG, "obtendo generos")
                var response: Response<Genres?>? = movieApi.fetchMovieGenres(it)
                withContext(Dispatchers.Main) {
                    postResourse(liveData, response, callback)
                }
            }
        }
        return liveData
    }

    fun <T> postResourse(liveData: MutableLiveData<Resource<T, Exception>>, response: Response<T?>?, callback: (Resource<T, Exception>) -> Unit) {
        if (response?.isSuccessful == true) {
            val result = response.body()!!
            val resource = Resource<T, Exception>(result)
            liveData.postValue(resource)
            callback(resource)
        }else if (response?.isSuccessful == false) {
            Log.d(TAG,"Falhou:\n${response.errorBody()?.string()}")
            val resource = Resource<T, Exception>(RuntimeException("Falha ao obter dados"))
            liveData.postValue(resource)
            callback(resource)
        } else {
            Log.i(TAG,"Falha na conexão")
            val resource = Resource<T, Exception>(ConnectException("Falha na conexão"))
            liveData.postValue(resource)
            callback(resource)
        }
    }

}