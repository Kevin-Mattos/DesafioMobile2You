package com.example.desafiomobile2you.repository.entities

import com.google.gson.annotations.SerializedName

data class SimilarMovies (
    val page: Long,
    val results: List<Movie>,

    @SerializedName("total_pages")
    val totalPages: Long,

    @SerializedName("total_results")
    val totalResults: Long
)
