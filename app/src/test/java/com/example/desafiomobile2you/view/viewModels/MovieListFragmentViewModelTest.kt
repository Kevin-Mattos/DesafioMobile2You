package com.example.desafiomobile2you.view.viewModels

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.desafiomobile2you.repository.MovieRepository
import com.example.desafiomobile2you.repository.entities.Genres
import com.example.desafiomobile2you.repository.entities.Movie
import com.example.desafiomobile2you.repository.entities.SimilarMovies
import com.example.desafiomobile2you.util.Resource
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class MovieListFragmentViewModelTest {


    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    lateinit var viewModel: MovieListFragmentViewModel

    lateinit var movieRepository: MovieRepository

    @Before
    fun setUp() {

        movieRepository = Mockito.mock(MovieRepository::class.java)
        whenever(movieRepository.fetchMovieGenres()).thenAnswer {
            MutableLiveData<Resource<Genres, Exception>>().apply { value = Resource(Genres(listOf())) }
        }
        viewModel = MovieListFragmentViewModel(Application(), movieRepository)

    }


    @Test
    fun fetchSimilarMovies_deve_alterar_valores_de_acordo_com_filmes_similares_recebido_quando_sucesso() {
        //prepare
        val expectedPopularity: String = "550"
        val expectedVoteCount: String = "450"
        val movie = mock<Movie> {
            on {id} doReturn 550
            on { popularity } doReturn  expectedPopularity.toDouble()
            on { voteCount } doReturn expectedVoteCount.toDouble()
        }
        val movies = listOf(movie)
        val similarMovies = mock<SimilarMovies> {
            on { results } doReturn  movies
        }
        whenever(movieRepository.fetchSimilarMovies(any(), any())).then {
            val liveData = MutableLiveData<Resource<SimilarMovies, Exception>>()
            liveData.value = Resource(similarMovies)
            it.getArgument<((Resource<SimilarMovies, Exception>) -> Unit)>(1)(liveData.value!!)
            liveData
        }

        //action
        val similarMoviesLiveData = viewModel.fetchSimilarMovies()

        //assertion
        Assert.assertEquals(movies, similarMoviesLiveData.value!!.data!!.results)
        Assert.assertNull(similarMoviesLiveData.value!!.error)

    }

    @Test
    fun fetchSimilarMovies_deve_receber_mensagem_de_erro_quando_falha() {        //prepare

        val expectedErrorMessage = "falha ao obter generos"
        whenever(movieRepository.fetchSimilarMovies(any(), any())).then {
            val liveData = MutableLiveData<Resource<SimilarMovies, Exception>>()
            liveData.value = Resource(Exception(expectedErrorMessage))
            it.getArgument<((Resource<SimilarMovies, Exception>) -> Unit)>(1)(liveData.value!!)
            liveData
        }

        //action
        val similarMoviesLiveData = viewModel.fetchSimilarMovies()

        //assertion
        Assert.assertEquals(expectedErrorMessage, similarMoviesLiveData.value!!.error)
        Assert.assertNull(similarMoviesLiveData.value!!.data)

    }

}