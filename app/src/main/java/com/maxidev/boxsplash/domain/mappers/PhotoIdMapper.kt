package com.maxidev.boxsplash.domain.mappers

import com.maxidev.boxsplash.data.remote.dto.PhotoIdDto
import com.maxidev.boxsplash.domain.model.LinksDomain
import com.maxidev.boxsplash.domain.model.PhotoIdDomain
import com.maxidev.boxsplash.domain.model.UrlsDomain

fun PhotoIdDto.asDomain() =
    this.let { data ->
        PhotoIdDomain(
            id = id.orEmpty(),
            createdAt = createdAt.orEmpty(),
            width = width ?: 0,
            height = height ?: 0,
            color = color.orEmpty(),
            blurHash = blurHash.orEmpty(),
            description = description.orEmpty(),
            altDescription = altDescription.orEmpty(),
            urls = UrlsDomain(
                raw = urls?.raw.orEmpty(),
                full = urls?.full.orEmpty(),
                regular = urls?.regular.orEmpty(),
                small = urls?.small.orEmpty(),
                thumb = urls?.thumb.orEmpty(),
                smallS3 = urls?.smallS3.orEmpty()
            ),
            links = LinksDomain(
                html = links?.html.orEmpty(),
                download = links?.download.orEmpty()
            ),
            user = PhotoIdDomain.UserDomain(
                id = user?.id.orEmpty(),
                username = user?.username.orEmpty(),
                name = user?.name.orEmpty(),
                portfolioUrl = user?.portfolioUrl.orEmpty(),
                links = PhotoIdDomain.UserDomain.UserLinksDomain(
                    html = user?.links?.html.orEmpty(),
                    photos = user?.links?.photos.orEmpty(),
                    portfolio = user?.links?.portfolio.orEmpty()
                ),
                profileImage = PhotoIdDomain.UserDomain.ProfileImageDomain(
                    large = user?.profileImage?.large.orEmpty()
                )
            ),
            tags = data.tags?.map { it?.title.orEmpty() } ?: emptyList()
        )
    }