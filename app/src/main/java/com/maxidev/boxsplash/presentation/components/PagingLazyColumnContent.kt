package com.maxidev.boxsplash.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey

@Composable
fun <T: Any> PagingLazyColumnContent(
    modifier: Modifier = Modifier,
    pagingItems: LazyPagingItems<T>,
    key: ((item: T) -> Any),
    content: @Composable (T) -> Unit,
    lazyState: LazyListState = rememberLazyListState()
) {
    val spacing = 16.dp
    val contentPadding = PaddingValues(spacing)

    Box(modifier = modifier) {
        LazyColumn(
            state = lazyState,
            contentPadding = contentPadding,
            verticalArrangement = Arrangement.spacedBy(spacing),
            horizontalAlignment = Alignment.CenterHorizontally
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
                        item {
                            // Circular progress indicator
                        }
                    }

                    states.refresh is LoadState.Error -> {
                        // Exception check
                    }

                    states.append is LoadState.Loading -> {
                        item {
                            // Circular progress indicator
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