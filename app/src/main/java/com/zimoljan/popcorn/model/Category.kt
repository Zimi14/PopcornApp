package com.zimoljan.popcorn.model

import com.zimoljan.popcorn.data.remote.Movie


data class Category(
    val id: Long,
    val genre: String,
    val movies: List<Movie>
)
