package com.example.desafiomobile2you

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.desafiomobile2you.databinding.ActivityMainBinding
import com.example.desafiomobile2you.viewModels.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private val TAG = this.javaClass.canonicalName

    private val mBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val mViewModel: MainActivityViewModel by lazy {
        ViewModelProvider.AndroidViewModelFactory(application).create(
            MainActivityViewModel::class.java
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        setSupportActionBar(mBinding.toolbar);
        setupBinding()

        mViewModel.movieGenres.observe(this, Observer { genreResource ->
            if (genreResource.success) {
                observeImage()
                mViewModel.fetchDetails(550)
            }
        })

    }

    private fun observeImage() {
        mViewModel.movieUrl.observe(this, Observer {
            it?.let{
                Glide.with(this).load(it).into(mBinding.expandedImage)
            }
        })
    }

    private fun setupBinding() {
        mBinding.viewModel = mViewModel
        mBinding.lifecycleOwner = this
    }
}