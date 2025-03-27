package com.maxidev.boxsplash.domain.repository

import androidx.paging.PagingData
import com.maxidev.boxsplash.domain.model.CollectionDomain
import com.maxidev.boxsplash.domain.model.PhotoDomain
import com.maxidev.boxsplash.domain.model.TopicsDomain
import kotlinx.coroutines.flow.Flow

interface PhotosRepository {

    fun fetchPhotos(): Flow<PagingData<PhotoDomain>>

    fun fetchCollections(): Flow<PagingData<CollectionDomain>>

    fun fetchTopics(): Flow<PagingData<TopicsDomain>>
}