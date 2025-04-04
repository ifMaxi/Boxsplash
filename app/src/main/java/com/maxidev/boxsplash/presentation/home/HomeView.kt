@file:OptIn(ExperimentalMaterial3Api::class)

package com.maxidev.boxsplash.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.maxidev.boxsplash.PhotoDetailScreen
import com.maxidev.boxsplash.R
import com.maxidev.boxsplash.SearchScreen
import com.maxidev.boxsplash.presentation.components.CollectionCardComposable
import com.maxidev.boxsplash.presentation.components.IconButtonComposable
import com.maxidev.boxsplash.presentation.components.ImageComposable
import com.maxidev.boxsplash.presentation.components.PagingLazyColumnContent
import com.maxidev.boxsplash.presentation.components.PagingLazyRowContent
import com.maxidev.boxsplash.presentation.components.PagingStaggeredLazyScreenContent
import com.maxidev.boxsplash.presentation.components.TopBarComposable
import com.maxidev.boxsplash.presentation.components.TopicCardComposable

@Composable
fun HomeView(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.homeState.collectAsStateWithLifecycle()

    ScreenContent(
        state = state,
        onEvent = { event ->
            when (event) {
                HomeUiEvents.NavigateToBookmarks -> TODO()
                HomeUiEvents.NavigateToSearch -> {
                    navController.navigate(SearchScreen)
                }
                HomeUiEvents.NavigateToSettings -> TODO()
                is HomeUiEvents.NavigateToTopicContent -> TODO()
                is HomeUiEvents.NavigateToCollectionContent -> TODO()
                is HomeUiEvents.NavigateToPhotoDetail -> {
                    navController.navigate(PhotoDetailScreen(event.id))
                }
            }
        }
    )
}

@Composable
private fun ScreenContent(
    state: HomeUiState,
    onEvent: (HomeUiEvents) -> Unit
) {
    val photos = state.photos.collectAsLazyPagingItems()
    val collections = state.collections.collectAsLazyPagingItems()
    val topics = state.topics.collectAsLazyPagingItems()
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }
    val textIcons = listOf<Pair<String, ImageVector>>(
        Pair("Photos", Icons.Default.Photo),
        Pair("Collections", Icons.Default.Folder)
    )
    val tabIcons = listOf(Icons.Default.Photo, Icons.Default.Folder)

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopBarComposable(
                title = "Boxsplash",
                actions = {
                    IconButtonComposable(
                        icon = Icons.Default.Search,
                        contentDescription = R.string.search,
                        onClick = {
                            onEvent(HomeUiEvents.NavigateToSearch)
                        }
                    )
                    IconButtonComposable(
                        icon = Icons.Default.Bookmark,
                        contentDescription = R.string.bookmarcks,
                        onClick = {
                            onEvent(HomeUiEvents.NavigateToBookmarks)
                        }
                    )
                    IconButtonComposable(
                        icon = Icons.Default.Settings,
                        contentDescription = R.string.settings,
                        onClick = {
                            onEvent(HomeUiEvents.NavigateToSettings)
                        }
                    )
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            PrimaryTabRow(selectedTabIndex = selectedTab) {
                textIcons.forEachIndexed { index, tab ->
                    Tab(
                        selected = selectedTab == index,
                        text = {
                            Text(
                                text = tab.first
                            )
                        },
                        icon = {
                            Icon(
                                imageVector = tab.second,
                                contentDescription = null
                            )
                        },
                        onClick = { selectedTab = index }
                    )
                }
            }

            when (selectedTab) {
                0 -> {
                    PagingStaggeredLazyScreenContent(
                        modifier = Modifier.consumeWindowInsets(innerPadding),
                        pagingItems = photos,
                        key = { it.id },
                        content = {
                            ImageComposable(
                                image = it.urls.small,
                                contentScale = ContentScale.Crop,
                                onClick = {
                                    onEvent(
                                        HomeUiEvents.NavigateToPhotoDetail(
                                            id = it.id
                                        )
                                    )
                                }
                            )
                        }
                    )
                }
                1 -> {
                    Column(
                        modifier = Modifier.consumeWindowInsets(innerPadding),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "Collections",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(10.dp)
                        )
                        PagingLazyRowContent(
                            modifier = Modifier.height(200.dp),
                            pagingItems = collections,
                            key = { it.id },
                            content = {
                                CollectionCardComposable(
                                    title = it.title,
                                    image = it.coverPhotos.urls.regular,
                                    totalPhotos = it.totalPhotos,
                                    onClick = {
                                        onEvent(
                                            HomeUiEvents.NavigateToCollectionContent(
                                                id = it.id
                                            )
                                        )
                                    }
                                )
                            }
                        )

                        Text(
                            text = "Topics",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(10.dp)
                        )
                        PagingLazyColumnContent(
                            //modifier = Modifier.consumeWindowInsets(innerPadding),
                            pagingItems = topics,
                            key = { it.id },
                            content = {
                                TopicCardComposable(
                                    title = it.title,
                                    image = it.coverPhotos.urls.regular,
                                    onClick = {
                                        HomeUiEvents.NavigateToTopicContent(
                                            id = it.id
                                        )
                                    }
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

// TODO: Image loading effect.
// TODO: Optimize image loading in photos layer.
// TODO: Paging states in all layers.
// TODO: Go to top FAB.
// TODO: Navigation events.
// TODO: Window insets in status bar and navigation bar.
// TODO: Add animations to layers when change.