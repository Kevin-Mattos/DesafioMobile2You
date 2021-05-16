package com.example.desafiomobile2you.repository.factories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.desafiomobile2you.repository.MovieRepository
import com.example.desafiomobile2you.view.viewModels.MainActivityViewModel

class MainActivityViewModelFactory(val application: Application, val movieRepository: MovieRepository): ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainActivityViewModel::class.java)){
            return  MainActivityViewModel(application, movieRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}