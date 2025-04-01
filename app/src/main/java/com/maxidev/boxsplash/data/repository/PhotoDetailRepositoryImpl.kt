package com.maxidev.boxsplash.data.repository

import com.maxidev.boxsplash.data.remote.UnsplashApiService
import com.maxidev.boxsplash.domain.mappers.asDomain
import com.maxidev.boxsplash.domain.model.PhotoIdDomain
import com.maxidev.boxsplash.domain.repository.PhotoDetailRepository
import javax.inject.Inject

class PhotoDetailRepositoryImpl @Inject constructor(
    private val api: UnsplashApiService
): PhotoDetailRepository {

    override suspend fun fetchPhotoDetail(id: String): PhotoIdDomain =
        api.getPhotoId(id).asDomain()
}