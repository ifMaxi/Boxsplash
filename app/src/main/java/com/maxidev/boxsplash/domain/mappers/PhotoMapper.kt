package com.maxidev.boxsplash.domain.mappers

import com.maxidev.boxsplash.data.local.entity.PhotosEntity
import com.maxidev.boxsplash.data.local.entity.UrlsEntity
import com.maxidev.boxsplash.data.remote.dto.PhotosDto
import com.maxidev.boxsplash.domain.model.PhotoDomain
import com.maxidev.boxsplash.domain.model.UrlsDomain

fun PhotosDto.asExternal() =
    this.let { data ->
        PhotosEntity(
            id = data.id.orEmpty(),
            color = data.color.orEmpty(),
            blurHash = data.blurHash.orEmpty(),
            urls = UrlsEntity(
                raw = data.urls?.raw.orEmpty(),
                full = data.urls?.full.orEmpty(),
                regular = data.urls?.regular.orEmpty(),
                small = data.urls?.small.orEmpty(),
                thumb = data.urls?.thumb.orEmpty(),
                smallS3 = data.urls?.smallS3.orEmpty()
            )
        )
    }

fun PhotosEntity.asDomain() =
    PhotoDomain(
        id = id,
        color = color,
        blurHash = blurHash,
        urls = UrlsDomain(
            raw = urls.raw,
            full = urls.full,
            regular = urls.regular,
            small = urls.small,
            thumb = urls.thumb,
            smallS3 = urls.smallS3
        )
    )