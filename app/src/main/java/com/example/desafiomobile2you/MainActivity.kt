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

    }
}