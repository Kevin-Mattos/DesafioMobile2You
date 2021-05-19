package com.example.desafiomobile2you.repository.entities

import com.google.gson.annotations.SerializedName

data class Movie (

    val id: Long,

    @SerializedName("genre_ids")
    val genreIds: List<Long>,

    val popularity: Double,

    @SerializedName("poster_path")
    val posterPath: String,

    @SerializedName("release_date")
    val releaseDate: String,

    var title: String,

    @SerializedName("vote_count")
    val voteCount: Double
) {
    var isSelected = false
}
