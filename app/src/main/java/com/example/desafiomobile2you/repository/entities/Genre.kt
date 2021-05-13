package com.example.desafiomobile2you.repository.entities

data class Genre (
    val id: Long,
    val name: String
)

data class Genres (
    val genres: List<Genre>
)