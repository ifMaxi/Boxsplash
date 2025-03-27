package com.maxidev.boxsplash.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey

@Composable
fun <T: Any> PagingStaggeredLazyScreenContent(
    modifier: Modifier = Modifier,
    pagingItems: LazyPagingItems<T>,
    key: ((item: T) -> Any),
    content: @Composable (T) -> Unit,
    lazyState: LazyStaggeredGridState = rememberLazyStaggeredGridState()
) {
    val cells = StaggeredGridCells.Adaptive(150.dp)
    val spacing = 8.dp
    val contentPadding = PaddingValues(spacing)

    Box(modifier = modifier) {
        LazyVerticalStaggeredGrid(
            columns = cells,
            state = lazyState,
            contentPadding = contentPadding,
            horizontalArrangement = Arrangement.spacedBy(spacing),
            verticalItemSpacing = spacing
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
                        item(span = StaggeredGridItemSpan.FullLine) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        }
                    }

                    states.refresh is LoadState.Error -> {
                        // Exception check
                    }

                    states.append is LoadState.Loading -> {
                        item(span = StaggeredGridItemSpan.FullLine) {
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