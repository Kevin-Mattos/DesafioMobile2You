package com.example.desafiomobile2you.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.desafiomobile2you.R
import com.example.desafiomobile2you.databinding.FragmentFailedToLoadMovieBinding
import com.example.desafiomobile2you.databinding.FragmentMovieListBinding
class FailedToLoadMovieFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    var observe: (() -> Unit) -> Unit = {}
    var retry: () -> Unit = {}

    lateinit var mBinding: FragmentFailedToLoadMovieBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentFailedToLoadMovieBinding.inflate(inflater, container, false)
        observe {
            mBinding.retryButton.revertAnimation()
        }

        mBinding.retryButton.setOnClickListener {
            mBinding.retryButton.startAnimation()
            retry()
        }


        return mBinding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.retryButton?.dispose()
    }

}