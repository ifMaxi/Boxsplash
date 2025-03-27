package com.maxidev.boxsplash.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.filter
import com.maxidev.boxsplash.data.local.AppDataBase
import com.maxidev.boxsplash.data.local.entity.SearchHistoryEntity
import com.maxidev.boxsplash.data.paging.SearchPhotosPagingSource
import com.maxidev.boxsplash.data.remote.UnsplashApiService
import com.maxidev.boxsplash.domain.mappers.asDomain
import com.maxidev.boxsplash.domain.mappers.asEntity
import com.maxidev.boxsplash.domain.model.PhotoDomain
import com.maxidev.boxsplash.domain.model.SearchHistoryDomain
import com.maxidev.boxsplash.domain.repository.SearchRepository
import com.maxidev.boxsplash.utils.ConstantsUtils.PAGE_SIZE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

@OptIn(FlowPreview::class)
class SearchRepositoryImpl @Inject constructor(
    private val api: UnsplashApiService,
    db: AppDataBase
): SearchRepository {

    private val historySearch = db.searchHistoryDao()

    @OptIn(FlowPreview::class)
    override fun searchPhotos(query: String): Flow<PagingData<PhotoDomain>> {
        val sourceFactory = { SearchPhotosPagingSource(api = api, query = query) }
        val pagingConfig = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = true
        )

        return Pager(
            config = pagingConfig,
            pagingSourceFactory = sourceFactory
        ).flow
            .distinctUntilChanged()
            .debounce(500)
            .map { pagingData ->
                val filterData = mutableSetOf<String>()

                pagingData.filter {
                    if (filterData.contains(it.id)) {
                        false
                    } else {
                        filterData.add(it.id)
                    }
                }
            }
    }

    override fun getRecentSearches(): Flow<List<SearchHistoryDomain>> =
        historySearch.getRecentSearches()
            .map { entity -> entity.map(SearchHistoryEntity::asDomain) }
            .flowOn(Dispatchers.IO)
            .debounce(500)
            .catch { e ->
                if (e is IOException) {
                    throw e
                }
                emit(emptyList())
            }

    override fun getSearchHistory(query: String): Flow<List<SearchHistoryDomain>> =
        historySearch.getSearchHistory(query)
            .map { entity -> entity.map(SearchHistoryEntity::asDomain) }
            .flowOn(Dispatchers.IO)
            .debounce(500)
            .catch { e ->
                if (e is IOException) {
                    throw e
                }
                emit(emptyList())
            }

    override suspend fun saveInput(entity: List<SearchHistoryDomain>) =
        withContext(Dispatchers.IO) {
            val data = entity.map { it.asEntity() }

            historySearch.upsertInputs(data)
        }

    override suspend fun deleteInput(input: String) =
        withContext(Dispatchers.IO) {
            historySearch.deleteInput(input)
        }
}