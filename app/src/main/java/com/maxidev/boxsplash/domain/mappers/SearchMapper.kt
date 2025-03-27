@file:OptIn(ExperimentalUuidApi::class)

package com.maxidev.boxsplash.domain.mappers

import com.maxidev.boxsplash.data.local.entity.SearchHistoryEntity
import com.maxidev.boxsplash.data.remote.dto.SearchDto
import com.maxidev.boxsplash.domain.model.PhotoDomain
import com.maxidev.boxsplash.domain.model.SearchHistoryDomain
import com.maxidev.boxsplash.domain.model.UrlsDomain
import kotlin.uuid.ExperimentalUuidApi

fun SearchDto.asDomain() =
    this.results?.map { data ->
        PhotoDomain(
            id = data.id.orEmpty(),
            color = data.color.orEmpty(),
            blurHash = data.blurHash.orEmpty(),
            urls = UrlsDomain(
                raw = data.urls?.raw.orEmpty(),
                full = data.urls?.full.orEmpty(),
                regular = data.urls?.regular.orEmpty(),
                small = data.urls?.small.orEmpty(),
                thumb = data.urls?.thumb.orEmpty(),
                smallS3 = data.urls?.smallS3.orEmpty()
            )
        )
    }

fun SearchHistoryEntity.asDomain() =
    SearchHistoryDomain(
        id = id,
        input = input,
        timestamp = timestamp
    )

fun SearchHistoryDomain.asEntity() =
    SearchHistoryEntity(
        id = id,
        input = input,
        timestamp = timestamp
    )