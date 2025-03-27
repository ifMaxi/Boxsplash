package com.maxidev.boxsplash.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.maxidev.boxsplash.data.local.AppDataBase
import com.maxidev.boxsplash.data.local.entity.CollectionsEntity
import com.maxidev.boxsplash.data.local.entity.PhotosEntity
import com.maxidev.boxsplash.data.local.entity.TopicsEntity
import com.maxidev.boxsplash.data.paging.CollectionsMediator
import com.maxidev.boxsplash.data.paging.PhotosMediator
import com.maxidev.boxsplash.data.paging.TopicsMediator
import com.maxidev.boxsplash.data.remote.UnsplashApiService
import com.maxidev.boxsplash.domain.mappers.asDomain
import com.maxidev.boxsplash.domain.model.CollectionDomain
import com.maxidev.boxsplash.domain.model.PhotoDomain
import com.maxidev.boxsplash.domain.model.TopicsDomain
import com.maxidev.boxsplash.domain.repository.PhotosRepository
import com.maxidev.boxsplash.utils.ConstantsUtils.PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class PhotosRepositoryImpl @Inject constructor(
    private val api: UnsplashApiService,
    private val db: AppDataBase
): PhotosRepository {

    private val photosDao = db.photosDao()
    private val collectionsDao = db.collectionsDao()
    private val topicsDao = db.topicsDao()

    override fun fetchPhotos(): Flow<PagingData<PhotoDomain>> {
        val sourceFactory = { photosDao.pagingSource() }
        val remoteMediator = PhotosMediator(api = api, db = db)
        val pagerConfig = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = true
        )

        return Pager(
            config = pagerConfig,
            remoteMediator = remoteMediator,
            pagingSourceFactory = sourceFactory
        ).flow
            .map { entity -> entity.map(PhotosEntity::asDomain) }
    }

    override fun fetchCollections(): Flow<PagingData<CollectionDomain>> {
        val sourceFactory = { collectionsDao.pagingSource() }
        val remoteMediator = CollectionsMediator(api = api, db = db)
        val pagerConfig = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = true
        )

        return Pager(
            config = pagerConfig,
            remoteMediator = remoteMediator,
            pagingSourceFactory = sourceFactory
        ).flow
            .map { entity -> entity.map(CollectionsEntity::asDomain) }
    }

    override fun fetchTopics(): Flow<PagingData<TopicsDomain>> {
        val sourceFactory = { topicsDao.topicSource() }
        val remoteMediator = TopicsMediator(api = api, db = db)
        val pagerConfig = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = true
        )

        return Pager(
            config = pagerConfig,
            remoteMediator = remoteMediator,
            pagingSourceFactory = sourceFactory
        ).flow
            .map { entity -> entity.map(TopicsEntity::asDomain) }
    }
}