package com.maxidev.boxsplash.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.maxidev.boxsplash.data.remote.UnsplashApiService
import com.maxidev.boxsplash.domain.mappers.asDomain
import com.maxidev.boxsplash.domain.model.PhotoDomain
import com.maxidev.boxsplash.utils.ConstantsUtils
import retrofit2.HttpException
import java.io.IOException

class SearchPhotosPagingSource(
    private val api: UnsplashApiService,
    private val query: String
): PagingSource<Int, PhotoDomain>() {

    override fun getRefreshKey(state: PagingState<Int, PhotoDomain>): Int? {

        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoDomain> {

        return try {

            val page = params.key ?: ConstantsUtils.INITIAL_PAGE
            val response = api.searchPhotos(
                query = query,
                page = page,
                perPage = params.loadSize,
            )
            val responseToDomain = response.asDomain()
            val endOfPagination = responseToDomain.isNullOrEmpty()
            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (endOfPagination) null else page + 1

            LoadResult.Page(
                data = responseToDomain.orEmpty(),
                prevKey = prevKey,
                nextKey = nextKey
            )

        } catch (ioException: IOException) {
            LoadResult.Error(ioException)
        } catch (httpException: HttpException) {
            LoadResult.Error(httpException)
        }
    }
}