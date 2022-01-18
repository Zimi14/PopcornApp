package com.zimoljan.popcorn.ui.screen.movies

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zimoljan.popcorn.R
import com.zimoljan.popcorn.data.remote.Movie
import com.zimoljan.popcorn.model.Category
import com.zimoljan.popcorn.ui.common.Fakes
import com.zimoljan.popcorn.ui.common.Poster
import com.zimoljan.popcorn.ui.theme.Popcorn
import com.zimoljan.popcorn.utils.calladapter.flow.Resource
import timber.log.Timber

@Composable
fun MoviesScreen(
    moviesViewModel: MoviesViewModel
) {
    val moviesResponseState by moviesViewModel.moviesResponse
        .observeAsState(initial = Resource.Initial())

    Timber.d("MoviesScreen: Resp is $moviesResponseState")

    val moviesRequest by moviesViewModel.moviesRequest.observeAsState()
    val currentUiMode = LocalConfiguration.current.uiMode

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.surface,
                elevation = 0.dp,
                modifier = Modifier.requiredHeight(70.dp),
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        fontSize = 28.sp
                    )
                },
                actions = {
                    AppBarMenu(
                        sortOrder = moviesRequest!!.sortOrder,
                        onSortByStarClicked = {
                            moviesViewModel.onSortByRatingClicked()
                        },
                        onSortByYearClicked = {
                            moviesViewModel.onSortByYearClicked()
                        },
                        onToggleDarkModelClicked = {
                            val isDark = currentUiMode and Configuration.UI_MODE_NIGHT_MASK ==
                                    Configuration.UI_MODE_NIGHT_YES
                            moviesViewModel.onToggleDarkModeClicked(isDark)
                        }
                    )
                }
            )
        }
    ) {
        Surface {
            BodyContent(
                moviesResponse = moviesResponseState,
                onMovieClicked = {
                    moviesViewModel.onMovieClicked(it)
                },
                onRetryClicked = {
                    moviesViewModel.onRetryClicked()
                }
            )
        }
    }
}

@Composable
fun AppBarMenu(
    sortOrder: Int,
    onSortByStarClicked: () -> Unit,
    onSortByYearClicked: () -> Unit,
    onToggleDarkModelClicked: () -> Unit
) {
    if (sortOrder == MoviesViewModel.SORT_ORDER_YEAR) {
        // Sort By Star
        IconButton(onClick = { onSortByStarClicked() }) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_star),
                contentDescription = null
            )
        }
    } else {
        // Sort By Year
        IconButton(onClick = { onSortByYearClicked() }) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_calendar),
                contentDescription = null
            )
        }
    }

    // Dark Mode
    IconButton(
        onClick = {
            onToggleDarkModelClicked()
        }
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_switch_dark_mode),
            contentDescription = null
        )
    }

}

@Composable
fun BodyContent(
    moviesResponse: Resource<List<Category>>,
    onMovieClicked: (Movie) -> Unit,
    onRetryClicked: () -> Unit
) {
    when (moviesResponse) {

        is Resource.Initial, is Resource.Loading -> {
            Timber.d("MoviesScreen: Loading")
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        is Resource.Success -> {
            Timber.d("MoviesScreen: Success")
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(moviesResponse.data) { _, category ->
                    CategoryRow(
                        category = category,
                        onMovieClicked = onMovieClicked
                    )
                }
            }
        }
    }
}


private val cardWidth = 150.dp

@Composable
fun MovieItem(
    movie: Movie,
    onMovieClicked: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .requiredWidth(cardWidth)
            .padding(10.dp)
    ) {

        // Poster
        Poster(
            modifier = Modifier
                .requiredWidth(cardWidth)
                .requiredHeight(200.dp),
            movie = movie,
            onMovieClicked = onMovieClicked
        )

        // Title
        Text(
            text = movie.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(top = 4.dp)
        )

        // Rating
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Star
            Icon(
                modifier = Modifier
                    .padding(end = 4.dp)
                    .requiredSize(12.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_rating),
                tint = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
                contentDescription = null
            )

            // Rating
            Text(
                text = movie.rating.toString(),
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
                style = MaterialTheme.typography.overline,
                modifier = Modifier.padding(top = 2.dp)
            )

        }
    }
}


@Composable
fun CategoryRow(
    modifier: Modifier = Modifier,
    category: Category,
    onMovieClicked: (Movie) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        // Category Title
        CategoryTitle(category)

        LazyRow {
            itemsIndexed(category.movies) { index, movie ->
                MovieItem(movie = movie, onMovieClicked = onMovieClicked)
            }
        }
    }
}

@Composable
private fun CategoryTitle(category: Category) {
    Text(
        text = category.genre,
        style = MaterialTheme.typography.subtitle2,
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
        modifier = Modifier.padding(
            start = 20.dp,
            top = 15.dp,
            bottom = 15.dp
        )
    )
}

@Preview
@Composable
fun PreviewCategory() {
    Popcorn {
        CategoryRow(
            category = getFakeCategory()
        ) { movie ->
        }
    }
}

@Composable
private fun getFakeCategory() = Category(
    id = 0,
    genre = "Action",
    movies = mutableListOf<Movie>().apply {
        repeat(10) {
            Fakes.getFakeMovie()
        }
    }
)

@Preview
@Composable
fun PreviewMovie() {
    Popcorn {
        MovieItem(
            movie = Fakes.getFakeMovie(),
            onMovieClicked = { /*TODO*/ }
        )
    }
}

