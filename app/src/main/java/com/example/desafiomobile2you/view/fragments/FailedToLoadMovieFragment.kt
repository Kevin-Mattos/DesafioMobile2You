package com.example.desafiomobile2you.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.desafiomobile2you.databinding.FragmentFailedToLoadMovieBinding

class FailedToLoadMovieFragment : Fragment() {

    var observe: (() -> Unit) -> Unit = {}

    var retry: () -> Unit = {}

    lateinit var mBinding: FragmentFailedToLoadMovieBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupBinding(inflater, container)
        setupObservers()
        return mBinding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.retryButton.dispose()
    }

    private fun setupBinding(inflater: LayoutInflater, container: ViewGroup?) {
        mBinding = FragmentFailedToLoadMovieBinding.inflate(inflater, container, false)
    }

    private fun setupObservers() {
        observe {
            mBinding.retryButton.revertAnimation()
        }

        mBinding.retryButton.setOnClickListener {
            mBinding.retryButton.startAnimation()
            retry()
        }
    }


}