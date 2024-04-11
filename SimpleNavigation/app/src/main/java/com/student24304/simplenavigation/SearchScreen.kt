package com.student24304.simplenavigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue


@Composable
fun SearchScreen(
    searchQuery: String,
    onNavigateToSecondScreen: (Int) -> Unit,
    paddingValues: PaddingValues,
    movieViewModel: MovieViewModel
) {
    LaunchedEffect(key1 = searchQuery) {
        movieViewModel.onSearchTextChange(searchQuery)
    }

    val movies by movieViewModel.searchedMovies.collectAsState()

    MovieList(movies = movies, contentPadding = paddingValues, onNavigateToSecondScreen = {
        onNavigateToSecondScreen(it.id)
    })

}
