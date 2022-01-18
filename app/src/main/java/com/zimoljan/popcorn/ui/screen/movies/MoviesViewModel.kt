package com.zimoljan.popcorn.ui.screen.movies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.zimoljan.popcorn.data.remote.Movie
import com.zimoljan.popcorn.data.repo.MoviesRepo
import com.zimoljan.popcorn.model.Category
import com.zimoljan.popcorn.model.MoviesRequest
import com.zimoljan.popcorn.utils.calladapter.flow.Resource
import com.zimoljan.popcorn.utils.flow.mutableEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject


@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesRepo: MoviesRepo
) : ViewModel() {


    companion object {

        const val SORT_ORDER_YEAR = 1
        const val SORT_ORDER_RATING = 2

        /**
         * To convert movie list to categorized feed
         */
        fun convertToFeed(movies: List<Movie>, sortOrder: Int): List<Category> {

            val genreSet = mutableSetOf<String>()

            for (movie in movies) {
                for (genre in movie.genre) {
                    genreSet.add(genre)
                }
            }

            val feedItems = mutableListOf<Category>()

            for ((index, genre) in genreSet.withIndex()) {

                val genreMovies = movies
                    .filter { it.genre.contains(genre) }
                    .sortedByDescending {
                        when (sortOrder) {
                            SORT_ORDER_RATING -> it.rating
                            SORT_ORDER_YEAR -> it.year?.toFloat()
                            else -> {
                                throw IllegalArgumentException("Sort order '$sortOrder' not managed")
                            }
                        }
                    }

                feedItems.add(
                    Category(
                        index.toLong(),
                        genre,
                        genreMovies
                    )
                )
            }
            return feedItems
        }
    }


    private val _toggleDarkMode = mutableEventFlow<Boolean>()
    val toggleDarkMode: SharedFlow<Boolean> = _toggleDarkMode

    private val _sortOrderToast = mutableEventFlow<Int>()
    val sortOrderToast: SharedFlow<Int> = _sortOrderToast

    val moviesRequest = MutableLiveData(MoviesRequest(SORT_ORDER_YEAR))

    // When ever sortOrder changed, load movies
    val moviesResponse = moviesRequest.switchMap { moviesRequest ->
        liveData<Resource<List<Category>>> {
            moviesRepo
                .getTopMovies()
                .onStart {
                    emit(Resource.Loading())
                }
                .catch {
                    emit(Resource.Error(it.message ?: "Something went wrong"))
                }
                .collect { movies ->
                    _sortOrderToast.emit(moviesRequest.sortOrder)

                    val feedItems = convertToFeed(movies, moviesRequest.sortOrder)

                    emit(Resource.Success(null, feedItems))
                }
        }
    }

    private val _goToMovieDetail = mutableEventFlow<Int>()
    val goToMovieDetail: SharedFlow<Int> = _goToMovieDetail

    fun onMovieClicked(it: Movie) {
        _goToMovieDetail.tryEmit(it.id)
    }

    fun onToggleDarkModeClicked(isDarkMode: Boolean) {
        _toggleDarkMode.tryEmit(isDarkMode.not())
    }

    fun onSortByRatingClicked() {
        moviesRequest.value = MoviesRequest(SORT_ORDER_RATING)
    }

    fun onSortByYearClicked() {
        moviesRequest.value = MoviesRequest(SORT_ORDER_YEAR)
    }

    fun onRetryClicked() {
        // Resetting sort order to fire new data request
        moviesRequest.value = moviesRequest.value!!.copy()
    }

}