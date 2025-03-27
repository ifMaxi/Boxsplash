package com.maxidev.boxsplash.domain.model

data class PhotoIdDomain(
    val id: String,
    val createdAt: String,
    val width: Int,
    val height: Int,
    val color: String,
    val blurHash: String,
    val description: String,
    val altDescription: String,
    val urls: UrlsDomain,
    val links: LinksDomain,
    val user: UserDomain,
    val exif: ExifDomain,
    val location: LocationDomain,
    val tags: List<TagDomain>,
    val relatedCollections: RelatedCollectionsDomain
) {
    data class UserDomain(
        val id: String,
        val username: String,
        val name: String,
        val portfolioUrl: String,
        val bio: String,
        val location: String,
        val links: UserLinksDomain,
        val profileImage: ProfileImageDomain
    ) {
        data class UserLinksDomain(
            val html: String,
            val photos: String,
            val portfolio: String
        )
        data class ProfileImageDomain(
            val small: String,
            val medium: String,
            val large: String
        )
    }

    data class RelatedCollectionsDomain(
        val result: List<ResultDomain>
    ) {
        data class ResultDomain(
            val id: String,
            val title: String,
            val totalPhotos: Int,
            val previewPhotos: List<PreviewPhotoDomain>
        ) {
            data class PreviewPhotoDomain(
                val urls: UrlsDomain
            )
        }
    }
}