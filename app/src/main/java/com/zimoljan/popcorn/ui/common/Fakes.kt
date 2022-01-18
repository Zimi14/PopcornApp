package com.zimoljan.popcorn.ui.common

import com.zimoljan.popcorn.data.remote.Movie


object Fakes {
    fun getFakeMovie() = Movie(
        actors = mutableListOf<String>()
            .apply {
                repeat(4) {
                    add("Actor $it")
                }
            },
        desc = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim venia",
        directors = mutableListOf<String>()
            .apply {
                repeat(3) {
                    add("Director $it")
                }
            },
        genre = mutableListOf<String>()
            .apply {
                repeat(3) {
                    add("Genre $it")
                }
            },
        imageUrl = "",
        thumbUrl = "https://picsum.photos/id/234/400/600",
        imdbPath = "/title/tt0082096",
        title = "Ironman",
        rating = 8f,
        year = 0
    )
}