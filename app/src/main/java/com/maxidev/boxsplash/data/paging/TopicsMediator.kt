package com.maxidev.boxsplash.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.maxidev.boxsplash.data.local.AppDataBase
import com.maxidev.boxsplash.data.local.entity.TopicsEntity
import com.maxidev.boxsplash.data.local.entity.remotekey.TopicRemoteKeyEntity
import com.maxidev.boxsplash.data.remote.UnsplashApiService
import com.maxidev.boxsplash.domain.mappers.asEntity
import com.maxidev.boxsplash.utils.ConstantsUtils.INITIAL_PAGE
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class TopicsMediator(
    private val api: UnsplashApiService,
    private val db: AppDataBase
): RemoteMediator<Int, TopicsEntity>() {

    private val topicsDao = db.topicsDao()
    private val remoteKeysDao = db.topicsRemoteKeyDao()
    private val timer = System.currentTimeMillis()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TopicsEntity>
    ): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val position = getRemoteKeyClosestToCurrentPosition(state)
                position?.nextKey?.plus(1) ?: INITIAL_PAGE
            }
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
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
            val response = api.getTopics(
                page = page,
                perPage = perPage,
                orderBy = "position"
            )
            val endOfPaginationReached = response.size < perPage
            val nextKey = if (endOfPaginationReached) null else page.plus(1)

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    topicsDao.clearAll()
                    remoteKeysDao.deleteRemoteKeys()
                }

                val remoteKey = response.map { key ->
                    TopicRemoteKeyEntity(
                        id = key.id.orEmpty(),
                        nextKey = nextKey,
                        lastUpdate = timer
                    )
                }

                val responseAsEntity = response.map { it.asEntity() }

                remoteKeysDao.upsertKeys(remoteKey)
                topicsDao.upsertAll(responseAsEntity)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, TopicsEntity>
    ): TopicRemoteKeyEntity? {

        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                remoteKeysDao.getRemoteKeys(id)
            }
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, TopicsEntity>
    ): TopicRemoteKeyEntity? {

        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let {
                remoteKeysDao.getRemoteKeys(it.id)
            }
    }
}