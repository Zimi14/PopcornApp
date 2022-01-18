package com.zimoljan.popcorn.ui.screen.detail

import androidx.lifecycle.ViewModel
import com.zimoljan.popcorn.data.remote.Movie
import com.zimoljan.popcorn.data.repo.MoviesRepo
import com.zimoljan.popcorn.utils.flow.mutableEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject


@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val moviesRepo: MoviesRepo
) : ViewModel() {

    private val _movieId = MutableStateFlow(-1)

    val movie = _movieId.mapLatest {
        moviesRepo.getMovie(it)
    }

    private val _openImdbUrl = mutableEventFlow<String>()
    val openImdbUrl: SharedFlow<String> = _openImdbUrl

    private val _shouldNavigateUp = mutableEventFlow<Boolean>()
    val shouldNavigateUp: SharedFlow<Boolean> = _shouldNavigateUp

    fun init(movieId: Int) {
        _movieId.tryEmit(movieId)
    }

    fun onOpenImdbClicked(movie: Movie) {
        _openImdbUrl.tryEmit("https://m.imdb.com${movie.imdbPath}")
    }

    fun onBackPressed() {
        _shouldNavigateUp.tryEmit(true)
    }
}