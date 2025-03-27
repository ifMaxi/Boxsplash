package com.maxidev.boxsplash.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.maxidev.boxsplash.data.local.AppDataBase
import com.maxidev.boxsplash.data.local.entity.PhotosEntity
import com.maxidev.boxsplash.data.local.entity.remotekey.PhotosRemoteKeysEntity
import com.maxidev.boxsplash.data.remote.UnsplashApiService
import com.maxidev.boxsplash.domain.mappers.asExternal
import com.maxidev.boxsplash.utils.ConstantsUtils.INITIAL_PAGE
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class PhotosMediator(
    private val api: UnsplashApiService,
    private val db: AppDataBase
): RemoteMediator<Int, PhotosEntity>() {

    private val photosDao = db.photosDao()
    private val remoteKeysDao = db.photosRemoteKeyDao()
    private val time = System.currentTimeMillis()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PhotosEntity>
    ): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val position = getRemoteKeyClosestToCurrentPosition(state)
                position?.nextKey?.plus(1) ?: INITIAL_PAGE
            }
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
//                val firstItem = getRemoteKeyForFirstItem(state)
//                val prevKey = firstItem?.prevKey
//                    ?: return MediatorResult.Success(
//                        endOfPaginationReached = firstItem != null
//                    )
//                prevKey
            }
            LoadType.APPEND -> {
                val lastItem = getRemoteKeyForLastItem(state)
                val nextPage = lastItem?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = true)

                nextPage
            }
        }

        return try {

            val perPage = state.config.pageSize
            val response = api.getPhotos(
                page = page,
                perPage = perPage
            )
            val endOfPaginationReached = response.size < perPage
            val nextKey = if (endOfPaginationReached) null else page.plus(1)

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    remoteKeysDao.deleteRemoteKeys()
                    photosDao.clearAll()
                }

                val remoteKey = response.map { key ->
                    PhotosRemoteKeysEntity(
                        id = key.id.orEmpty(),
                        nextKey = nextKey,
                        lastUpdate = time
                    )
                }

                val responseAsEntity = response.map { it.asExternal() }

                remoteKeysDao.upsertKeys(remoteKey)
                photosDao.upsertAll(responseAsEntity)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, PhotosEntity>
    ): PhotosRemoteKeysEntity? {

        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                remoteKeysDao.getRemoteKeys(id)
            }
        }
    }

//    private suspend fun getRemoteKeyForFirstItem(
//        state: PagingState<Int, PhotosEntity>
//    ): RemoteKeysEntity? {
//
//        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
//            ?.let {
//                remoteKeysDao.getRemoteKeys(it.id)
//            }
//    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, PhotosEntity>
    ): PhotosRemoteKeysEntity? {

        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let {
                remoteKeysDao.getRemoteKeys(it.id)
            }
    }
}