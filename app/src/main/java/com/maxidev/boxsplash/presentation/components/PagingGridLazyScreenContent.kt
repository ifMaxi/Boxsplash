package com.maxidev.boxsplash.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey

@Composable
fun <T: Any> PagingGridLazyScreenContent(
    modifier: Modifier = Modifier,
    pagingItems: LazyPagingItems<T>,
    key: ((item: T) -> Any),
    content: @Composable (T) -> Unit,
    lazyState: LazyGridState = rememberLazyGridState()
) {
    val cells = GridCells.Adaptive(150.dp)
    val spacing = 8.dp
    val contentPadding = PaddingValues(spacing)

    Box(modifier = modifier) {
        LazyVerticalGrid(
            columns = cells,
            state = lazyState,
            contentPadding = contentPadding,
            horizontalArrangement = Arrangement.spacedBy(spacing),
            verticalArrangement = Arrangement.spacedBy(spacing)
        ) {
            items(
                count = pagingItems.itemCount,
                key = pagingItems.itemKey { key(it) }
            ) {
                val contentItem = pagingItems[it]

                if (contentItem != null) {
                    content(contentItem)
                }
            }

            pagingItems.loadState.let { states ->
                when {
                    states.refresh is LoadState.NotLoading && pagingItems.itemCount < 1 -> {
                        // No data available
                    }

                    states.refresh is LoadState.Loading -> {
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            CircularProgressIndicator()
                        }
                    }

                    states.refresh is LoadState.Error -> {
                        // Exception check
                    }

                    states.append is LoadState.Loading -> {
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            CircularProgressIndicator()
                        }
                    }

                    states.append is LoadState.Error -> {
                        // Error
                    }
                }
            }
        }
    }
}