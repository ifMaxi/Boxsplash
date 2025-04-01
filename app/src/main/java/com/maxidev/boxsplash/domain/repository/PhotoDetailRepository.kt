package com.maxidev.boxsplash.domain.repository

import com.maxidev.boxsplash.domain.model.PhotoIdDomain

interface PhotoDetailRepository {

    suspend fun fetchPhotoDetail(id: String): PhotoIdDomain
}