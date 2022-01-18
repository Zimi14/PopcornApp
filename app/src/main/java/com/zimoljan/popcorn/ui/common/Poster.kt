package com.zimoljan.popcorn.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.zimoljan.popcorn.data.remote.Movie

@Composable
fun Poster(
    modifier: Modifier = Modifier,
    movie: Movie,
    onMovieClicked: (Movie) -> Unit
) {
    Card(
        modifier = Modifier.clickable(onClick = { onMovieClicked(movie) })
    ) {
        Image(
            painter = rememberImagePainter(data = movie.thumbUrl, builder = {
                crossfade(true)
                scale(Scale.FILL)
            }),
            modifier = modifier,
            contentDescription = null
        )
    }
}