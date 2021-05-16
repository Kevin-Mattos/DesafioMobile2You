package com.example.desafiomobile2you.view.viewModels

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.desafiomobile2you.repository.MovieRepository
import com.example.desafiomobile2you.repository.entities.Genre
import com.example.desafiomobile2you.repository.entities.Genres
import com.example.desafiomobile2you.repository.entities.Movie
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

class MainActivityViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    lateinit var viewModel: MainActivityViewModel

    lateinit var movieRepository: MovieRepository

    @Before
    fun setUp() {

        movieRepository = Mockito.mock(MovieRepository::class.java)
        whenever(movieRepository.fetchMovieGenres()).thenAnswer {
            MutableLiveData<Resource<Genres, Exception>>().apply {
                value = Resource(Genres(listOf()))
            }
        }
        viewModel = MainActivityViewModel(Application(), movieRepository)

    }

    @Test
    fun fetchDetails_deve_alterar_valores_de_acordo_com_filme_recebido_quando_sucesso() {
        //prepare
        val expectedPopularity: String = "550"
        val expectedVoteCount: String = "450"
        val movie = mock<Movie> {
            on { id } doReturn 550
            on { popularity } doReturn expectedPopularity.toDouble()
            on { voteCount } doReturn expectedVoteCount.toDouble()
        }
        whenever(movieRepository.fetchDetails(any(), any())).then {
            val liveData = MutableLiveData<Resource<Movie, Exception>>()
            liveData.value = Resource(movie)
            it.getArgument<((Resource<Movie, Exception>) -> Unit)>(1)(liveData.value!!)
            liveData
        }

        //action
        viewModel.fetchDetails(movie.id.toInt())


        //assertion
        Assert.assertEquals(expectedPopularity, viewModel.selectedMoviePopularity.value)
        Assert.assertEquals(expectedVoteCount, viewModel.selectedMovieLikes.value)
        Assert.assertEquals(movie.id, viewModel.selectedMovie.value!!.id)


    }

    @Test
    fun fetchDetails_deve_receber_mensagem_de_erro_quando_falha() {
        //prepare
        val errorMessage = "falha ao obter filme"
        whenever(movieRepository.fetchDetails(any(), any())).then {
            val liveData = MutableLiveData<Resource<Movie, Exception>>()
            liveData.value = Resource(Exception(errorMessage))
            it.getArgument<((Resource<Movie, Exception>) -> Unit)>(1)(liveData.value!!)
            liveData
        }

        //action
        viewModel.fetchDetails(5)


        //assertion
        Assert.assertEquals("0", viewModel.selectedMoviePopularity.value)
        Assert.assertEquals("0", viewModel.selectedMovieLikes.value)
        Assert.assertNull(viewModel.selectedMovie.value)


    }

    @Test
    fun fetchMovieGenres_deve_alterar_generos_de_acordo_com_generos_recebidos_quando_sucesso() {
        //prepare

        val expectedGenreName = "nome do genero"
        val genres = mock<Genres> {
            on { genres } doReturn listOf(Genre(1, expectedGenreName))
        }
        whenever(movieRepository.fetchMovieGenres(any())).then {
            val liveData = MutableLiveData<Resource<Genres, Exception>>()
            liveData.value = Resource(genres)
            it.getArgument<((Resource<Genres, Exception>) -> Unit)>(0)(liveData.value!!)
            liveData
        }

        //action
        viewModel.fetchMovieGenres()


        //assertion
        Assert.assertTrue(viewModel.movieGenres.value!!.data!!.genres.isNotEmpty())
        Assert.assertEquals(expectedGenreName, viewModel.movieGenres.value!!.data!!.genres[0].name)
        Assert.assertNull(viewModel.movieGenres.value!!.error)

    }

    @Test
    fun fetchMovieGenres_deve_receber_erro_quando_falha() {

        //prepare
        val expectedErrorMessage = "falha ao obter generos"
        whenever(movieRepository.fetchMovieGenres(any())).then {
            val liveData = MutableLiveData<Resource<Genres, Exception>>()
            liveData.value = Resource(Exception(expectedErrorMessage))
            it.getArgument<((Resource<Genres, Exception>) -> Unit)>(0)(liveData.value!!)
            liveData
        }

        //action
        viewModel.fetchMovieGenres()

        //assertion
        Assert.assertEquals(viewModel.movieGenres.value!!.error, expectedErrorMessage)
        Assert.assertNotNull(viewModel.movieGenres.value!!.error)

    }


}