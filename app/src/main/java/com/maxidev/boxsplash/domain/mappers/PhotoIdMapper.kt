package com.maxidev.boxsplash.domain.mappers

import com.maxidev.boxsplash.data.remote.dto.PhotoIdDto
import com.maxidev.boxsplash.domain.model.ExifDomain
import com.maxidev.boxsplash.domain.model.LinksDomain
import com.maxidev.boxsplash.domain.model.LocationDomain
import com.maxidev.boxsplash.domain.model.PhotoIdDomain
import com.maxidev.boxsplash.domain.model.TagDomain
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
                bio = user?.bio.orEmpty(),
                location = user?.location.orEmpty(),
                links = PhotoIdDomain.UserDomain.UserLinksDomain(
                    html = user?.links?.html.orEmpty(),
                    photos = user?.links?.photos.orEmpty(),
                    portfolio = user?.links?.portfolio.orEmpty()
                ),
                profileImage = PhotoIdDomain.UserDomain.ProfileImageDomain(
                    small = user?.profileImage?.small.orEmpty(),
                    medium = user?.profileImage?.medium.orEmpty(),
                    large = user?.profileImage?.large.orEmpty()
                )
            ),
            exif = ExifDomain(
                make = exif?.make.orEmpty(),
                model = exif?.model.orEmpty(),
                name = exif?.name.orEmpty(),
                exposureTime = exif?.exposureTime.orEmpty(),
                aperture = exif?.aperture.orEmpty(),
                focalLength = exif?.focalLength.orEmpty(),
                iso = exif?.iso ?: 0
            ),
            location = LocationDomain(
                name = location?.name.orEmpty(),
                city = location?.city.orEmpty(),
                country = location?.country.orEmpty(),
                position = LocationDomain.PositionDomain(
                    latitude = location?.position?.latitude ?: 0.0,
                    longitude = location?.position?.longitude ?: 0.0
                )
            ),
            tags = listOfNotNull(
                TagDomain(
                    title = tags?.get(0)?.title.orEmpty()
                )
            ),
            relatedCollections = PhotoIdDomain.RelatedCollectionsDomain(
                result = listOf(
                    PhotoIdDomain.RelatedCollectionsDomain.ResultDomain(
                        id = relatedCollections?.results?.get(0)?.id.orEmpty(),
                        title = relatedCollections?.results?.get(0)?.title.orEmpty(),
                        totalPhotos = relatedCollections?.results?.get(0)?.totalPhotos ?: 0,
                        previewPhotos = listOf(
                            PhotoIdDomain.RelatedCollectionsDomain.ResultDomain.PreviewPhotoDomain(
                                urls = UrlsDomain(
                                    raw = relatedCollections?.results?.get(0)?.previewPhotos?.get(0)?.urls?.raw.orEmpty(),
                                    full = relatedCollections?.results?.get(0)?.previewPhotos?.get(0)?.urls?.full.orEmpty(),
                                    regular = relatedCollections?.results?.get(0)?.previewPhotos?.get(0)?.urls?.regular.orEmpty(),
                                    small = relatedCollections?.results?.get(0)?.previewPhotos?.get(0)?.urls?.small.orEmpty(),
                                    thumb = relatedCollections?.results?.get(0)?.previewPhotos?.get(0)?.urls?.thumb.orEmpty(),
                                    smallS3 = relatedCollections?.results?.get(0)?.previewPhotos?.get(0)?.urls?.smallS3.orEmpty()

                                )
                            )
                        )
                    )
                )
            )
        )
    }