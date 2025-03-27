package com.maxidev.boxsplash.domain.repository

import androidx.paging.PagingData
import com.maxidev.boxsplash.domain.model.PhotoDomain
import com.maxidev.boxsplash.domain.model.SearchHistoryDomain
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    fun searchPhotos(query: String): Flow<PagingData<PhotoDomain>>

    fun getRecentSearches(): Flow<List<SearchHistoryDomain>>

    fun getSearchHistory(query: String): Flow<List<SearchHistoryDomain>>

    suspend fun saveInput(entity: List<SearchHistoryDomain>)

    suspend fun deleteInput(input: String)
}