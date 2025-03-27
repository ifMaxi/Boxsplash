package com.maxidev.boxsplash.domain.mappers

import com.maxidev.boxsplash.data.local.entity.CoverPhotoEntity
import com.maxidev.boxsplash.data.local.entity.TopicsEntity
import com.maxidev.boxsplash.data.local.entity.UrlsEntity
import com.maxidev.boxsplash.data.remote.dto.TopicsDto
import com.maxidev.boxsplash.domain.model.CoverPhotosDomain
import com.maxidev.boxsplash.domain.model.TopicsDomain
import com.maxidev.boxsplash.domain.model.UrlsDomain

fun TopicsDto.asEntity() =
    this.let { data ->
        TopicsEntity(
            id = data.id.orEmpty(),
            title = data.title.orEmpty(),
            coverPhoto = CoverPhotoEntity(
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

fun TopicsEntity.asDomain() =
    TopicsDomain(
        id = id,
        title = title,
        coverPhotos = CoverPhotosDomain(
            blurHash = coverPhoto.blurHash,
            urls = UrlsDomain(
                raw = coverPhoto.urls.raw,
                full = coverPhoto.urls.full,
                regular = coverPhoto.urls.regular,
                small = coverPhoto.urls.small,
                thumb = coverPhoto.urls.thumb,
                smallS3 = coverPhoto.urls.smallS3
            )
        )
    )