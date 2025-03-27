package com.maxidev.boxsplash.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.maxidev.boxsplash.R
import com.maxidev.boxsplash.presentation.components.IconButtonComposable
import com.maxidev.boxsplash.presentation.components.ImageComposable
import com.maxidev.boxsplash.presentation.components.PagingStaggeredLazyScreenContent
import com.maxidev.boxsplash.presentation.components.SearchBarComposable
import com.maxidev.boxsplash.presentation.search.SearchViewModel
import kotlinx.coroutines.launch

@Composable
fun SearchView(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsLazyPagingItems()
    val recent by viewModel.recentHistory.collectAsStateWithLifecycle()
    val filterRecent by viewModel.searchHistory.collectAsStateWithLifecycle()
    val input by viewModel.query
    var expanded by viewModel.expanded
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            SearchBarComposable(
                query = input,
                isExpanded = expanded,
                onSearch = { input ->
                    scope.launch {
                        viewModel.searchPhoto(input)
                        viewModel.saveInput(input)
                    }
                    expanded = false
                    focusManager.clearFocus()
                },
                onQueryChange = viewModel::onQueryChange,
                onExpandedChange = viewModel::onExpandedChange,
                placeholder = {
                    Text(
                        text = "Search photos"
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                },
                trailingIcon = {
                    if (input.isNotEmpty()) {
                        IconButtonComposable(
                            icon = Icons.Default.Clear,
                            contentDescription = R.string.clear_search,
                            onClick = { viewModel.clearQuery() }
                        )
                    }
                },
                content = {
                    Column(
                        modifier = Modifier
                            .wrapContentHeight(Alignment.Top)
                            .fillMaxWidth()
                            .verticalScroll(scrollState),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // TODO: Optimize recompositions
                        // TODO: Create a reusable list item.
                        // TODO: Make search ui state.

                        when {
                            input.isEmpty() -> {
                                recent.recentHistory.forEach { element ->
                                    ListItem(
                                        headlineContent = {
                                            Text(
                                                text = element.input
                                            )
                                        },
                                        leadingContent = {
                                            Icon(
                                                imageVector = Icons.Default.History,
                                                contentDescription = null
                                            )
                                        },
                                        trailingContent = {
                                            IconButtonComposable(
                                                icon = Icons.Default.Delete,
                                                contentDescription = R.string.delete,
                                                onClick = { viewModel.deleteInput(input) }
                                            )
                                        },
                                        colors = ListItemDefaults.colors(
                                            containerColor = Color.Transparent
                                        ),
                                        modifier = Modifier
                                            .clickable {
                                                viewModel.onQueryChange(element.input)
                                                viewModel.searchPhoto(element.input)
                                                expanded = false
                                            }
                                            .fillMaxWidth()
                                            .padding(horizontal = 16.dp, vertical = 6.dp)
                                    )
                                }
                            }
                            else -> {
                                filterRecent.searchHistory
                                    .filter { it.input.contains(input) }
                                    .forEach { element ->
                                        ListItem(
                                            headlineContent = {
                                                Text(
                                                    text = element.input
                                                )
                                            },
                                            leadingContent = {
                                                Icon(
                                                    imageVector = Icons.Default.History,
                                                    contentDescription = null
                                                )
                                            },
                                            colors = ListItemDefaults.colors(
                                                containerColor = Color.Transparent
                                            ),
                                            modifier = Modifier
                                                .clickable {
                                                    viewModel.onQueryChange(element.input)
                                                    viewModel.searchPhoto(element.input)
                                                    expanded = false
                                                }
                                                .fillMaxWidth()
                                                .padding(horizontal = 16.dp, vertical = 6.dp)
                                        )
                                    }
                            }
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        PagingStaggeredLazyScreenContent(
            modifier = Modifier.consumeWindowInsets(innerPadding),
            pagingItems = state,
            key = { it.id },
            content = {
                ImageComposable(
                    image = it.urls.small,
                    onClick = {
                        /* Navigate to photo detail. */
                    }
                )
            }
        )
    }
}