package com.example.desafiomobile2you

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.desafiomobile2you.viewModels.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private val TAG = this.javaClass.canonicalName



    private val mViewModel: MainActivityViewModel by lazy {
        ViewModelProvider.AndroidViewModelFactory(application).create(
            MainActivityViewModel::class.java
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//
//        mViewModel.getDetails().observe(this, Observer {
//            it?.let{movie ->
//                val image = findViewById<ImageView>(R.id.myImage)
//                Log.d(TAG, "id: ${movie.id}\nbackdropPath: ${movie.backdropPath}\nhomepage: ${movie.homepage}\noriginalTitle: ${movie.originalTitle}\nvoteCount: ${movie.voteCount}\npopularity: ${movie.popularity}")
//                Glide.with(this).load(mViewModel.getImageUrl(movie.backdropPath)).into(image)
//            }
//        })

        mViewModel.getSimilarMovies().observe(this, Observer {
           it.results.forEach {movie ->
               Log.d(TAG, "${movie.id} ${movie.originalTitle} ${movie.releaseDate.substring(0 until 4)}\n\n")

           }
        })

        mViewModel.getMovieGenres().observe(this, Observer { genres ->
            Log.d(TAG, "genres: ${genres.genres} }\n\n")
        })

    }
}