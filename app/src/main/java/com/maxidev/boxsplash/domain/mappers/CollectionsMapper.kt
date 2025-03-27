package com.maxidev.boxsplash.domain.mappers

import com.maxidev.boxsplash.data.local.entity.CollectionsEntity
import com.maxidev.boxsplash.data.local.entity.CoverPhotoEntity
import com.maxidev.boxsplash.data.local.entity.UrlsEntity
import com.maxidev.boxsplash.data.remote.dto.CollectionsDto
import com.maxidev.boxsplash.domain.model.CollectionDomain
import com.maxidev.boxsplash.domain.model.CoverPhotosDomain
import com.maxidev.boxsplash.domain.model.UrlsDomain

fun CollectionsDto.asEntity() =
    this.let { data ->
        CollectionsEntity(
            id = data.id.orEmpty(),
            title = data.title.orEmpty(),
            totalPhotos = data.totalPhotos ?: 0,
            coverPhotos = CoverPhotoEntity(
                blurHash = data.coverPhoto?.blurHash.orEmpty(),
                urls = UrlsEntity(
                    raw = data.coverPhoto?.urls?.raw.orEmpty(),
                    full = data.coverPhoto?.urls?.full.orEmpty(),
                    regular = data.coverPhoto?.urls?.regular.orEmpty(),
                    small = data.coverPhoto?.urls?.small.orEmpty(),
                    thumb = data.coverPhoto?.urls?.thumb.orEmpty(),
                    smallS3 = data.coverPhoto?.urls?.smallS3.orEmpty()
                )
            )
        )
    }

fun CollectionsEntity.asDomain() =
    CollectionDomain(
        id = id,
        title = title,
        totalPhotos = totalPhotos,
        coverPhotos = CoverPhotosDomain(
            blurHash = coverPhotos.blurHash,
            urls = UrlsDomain(
                raw = coverPhotos.urls.raw,
                full = coverPhotos.urls.full,
                regular = coverPhotos.urls.regular,
                small = coverPhotos.urls.small,
                thumb = coverPhotos.urls.thumb,
                smallS3 = coverPhotos.urls.smallS3
            )
        )
    )