package com.maxidev.boxsplash.presentation.home

import androidx.paging.PagingData
import com.maxidev.boxsplash.domain.model.CollectionDomain
import com.maxidev.boxsplash.domain.model.PhotoDomain
import com.maxidev.boxsplash.domain.model.TopicsDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class HomeUiState(
    val photos: Flow<PagingData<PhotoDomain>> = emptyFlow(),
    val collections: Flow<PagingData<CollectionDomain>> = emptyFlow(),
    val topics: Flow<PagingData<TopicsDomain>> = emptyFlow()
)