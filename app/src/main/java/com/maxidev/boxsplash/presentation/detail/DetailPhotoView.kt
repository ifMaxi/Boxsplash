@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)

package com.maxidev.boxsplash.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.maxidev.boxsplash.R
import com.maxidev.boxsplash.domain.handlers.Resource
import com.maxidev.boxsplash.domain.model.PhotoIdDomain
import com.maxidev.boxsplash.presentation.components.ButtonComposable
import com.maxidev.boxsplash.presentation.components.IconButtonComposable
import com.maxidev.boxsplash.presentation.components.SmallFabComposable
import com.maxidev.boxsplash.presentation.components.TopBarComposable
import com.maxidev.boxsplash.presentation.theme.BoxsplashTheme
import com.maxidev.boxsplash.utils.DateTimeUtils.convertDateTime

@Composable
fun DetailPhotoView(
    id: String,
    navController: NavController,
    viewModel: DetailPhotoViewModel = hiltViewModel()
) {
    val state by viewModel.detailState.collectAsStateWithLifecycle()

    LaunchedEffect(String) {
        viewModel.photoDetailStatus(id)
    }

    LoadStatus(
        resource = state
    )
}

@Composable
private fun LoadStatus(
    resource: Resource<PhotoIdDomain>
) {
    // *
    when (resource) {
        is Resource.Error<PhotoIdDomain> -> {
            Text(text = resource.message.toString())
        }
        is Resource.Loading<PhotoIdDomain> -> {
            CircularProgressIndicator()
        }
        is Resource.Success<PhotoIdDomain> -> {
            ScreenContent(
                photo = resource.data ?: return,
                onBookmark = {},
                onDownload = {},
                onShare = {},
                onOpenInBrowser = {}
            )
        }
    }
}

@Composable
private fun ScreenContent(
    photo: PhotoIdDomain,
    onBookmark: () -> Unit,
    onDownload: () -> Unit,
    onShare: () -> Unit,
    onOpenInBrowser: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()
    val topBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topBarState)
    var showBottomSheet by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopBarComposable(
                colors = TopAppBarDefaults.topAppBarColors(Color.Transparent),
                actions = {
                    IconButtonComposable(
                        icon = Icons.Default.OpenInBrowser,
                        contentDescription = R.string.open_in_browser,
                        onClick = onOpenInBrowser,
                        tint = Color.White
                    )
                    IconButtonComposable(
                        icon = Icons.Default.Share,
                        contentDescription = R.string.share,
                        onClick = onShare,
                        tint = Color.White
                    )
                },
                navigationIcon = {
                    IconButtonComposable(
                        icon = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = R.string.back,
                        tint = Color.White,
                        onClick = {
                            /* Navigate back. */
                        }
                    )
                },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            SmallFabComposable(
                icon = Icons.Default.KeyboardArrowUp,
                contentDescription = R.string.open_sheet,
                onClick = { showBottomSheet = true  }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                //.fillMaxSize()
                .consumeWindowInsets(innerPadding)
                .background(color = Color.Black),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var scale by remember { mutableFloatStateOf(1f) }
            var offset by remember { mutableStateOf(Offset.Zero) }

            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(photo.width.toFloat() / photo.height.toFloat()),
                content = {
                    val state = rememberTransformableState { zoomChange, offsetChange, _ ->
                        scale = (scale * zoomChange).coerceAtLeast(1f)

                        val extraWidth = (scale / 1) * constraints.maxWidth
                        val extraHeight = (scale / 1) * constraints.maxHeight
                        val maxX = extraWidth / 2
                        val maxY = extraHeight / 2

                        offset = Offset(
                            x = (offset.x + scale * offsetChange.x).coerceIn(-maxX, maxX),
                            y = (offset.y + scale * offsetChange.y).coerceIn(-maxY, maxY)
                        )
                    }

                    AsyncImage(
                        model = photo.urls.full,
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxSize()
                            .graphicsLayer {
                                scaleX = scale
                                scaleY = scale
                                translationX = offset.x
                                translationY = offset.y
                            }
                        .transformable(state = state)
                    )
                }
            )
        }

        if (showBottomSheet) {
            Box {
                ModalBottomSheet(
                    onDismissRequest = { showBottomSheet = false },
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                    sheetState = sheetState,
                    tonalElevation = 16.dp,
                    content = {
                        SheetContent(
                            avatar = photo.user.profileImage.large,
                            username = photo.user.username,
                            name = photo.user.name,
                            createdAt = photo.createdAt,
                            width = photo.width,
                            height = photo.height,
                            color = photo.color,
                            description = photo.altDescription,
                            tags = photo.tags,
                            onBookmark = onBookmark,
                            onDownload = onDownload
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun SheetContent(
    modifier: Modifier = Modifier,
    avatar: String,
    username: String,
    name: String,
    createdAt: String,
    width: Int,
    height: Int,
    color: String,
    description: String,
    tags: List<String>,
    onBookmark: () -> Unit,
    onDownload: () -> Unit
) {
    val dateTimeConverter = convertDateTime(dateTime = createdAt)
    val toHex = Color(color.toColorInt())
    val textStyle = TextStyle().copy(
        fontSize = 14.sp,
        fontWeight = FontWeight.Light,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )

    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .wrapContentHeight(Alignment.Top)
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AsyncImage(
                    model = avatar,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                )
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "@${username}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light
                    )
                }
            }
            // Buttons
            ActionButtonsItem(
                onBookmark = onBookmark,
                onDownload = onDownload
            )
            // Information
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (description.isNotEmpty()) {
                    Text(
                        text = "Description: $description",
                        style = textStyle,
                        fontStyle = FontStyle.Italic
                    )
                }
                Text(
                    text = "Published: $dateTimeConverter Hs.",
                    style = textStyle
                )
                Text(
                    text = "Dimensions: $height x $width",
                    style = textStyle
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Color: ",
                        style = textStyle
                    )
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(20.dp)
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.outline,
                                shape = CircleShape
                            )
                            .background(color = toHex)
                    )
                }
                Text(
                    text = "Tags:",
                    style = textStyle
                )
                FlowRow(
                    modifier = Modifier
                        .wrapContentHeight(Alignment.Top)
                        .align(Alignment.CenterHorizontally),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    tags.forEach { item ->
                        Box(
                            modifier = Modifier
                                .wrapContentHeight()
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.outline,
                                    shape = RoundedCornerShape(20)
                                )
                                .padding(6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = item,
                                style = textStyle
                            )
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun ActionButtonsItem(
    modifier: Modifier = Modifier,
    onBookmark: () -> Unit,
    onDownload: () -> Unit
) {
    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            ButtonComposable(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.Bookmark,
                text = "Bookmark",
                shape = RoundedCornerShape(20),
                onClick = onBookmark
            )
            Spacer(modifier = Modifier.width(20.dp))
            ButtonComposable(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.Download,
                text = "Download",
                shape = RoundedCornerShape(20),
                onClick = onDownload
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SheetPreview() {
    BoxsplashTheme {
        SheetContent(
            modifier = Modifier.fillMaxSize(),
            avatar = "",
            username = "Lorem Impsum",
            name = "isLorem",
            createdAt = "2025-03-17T09:40:54Z",
            width = 1080,
            height = 1090,
            color = "#8ca6c0",
            description = "Lorem description",
            tags = listOf("Nature", "Color", "Games", "Food", "Cold", "Hot", "Ice", "Travel", "Art"),
            onBookmark = {},
            onDownload = {}
        )
    }
}