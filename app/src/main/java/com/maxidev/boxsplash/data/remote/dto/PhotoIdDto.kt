package com.maxidev.boxsplash.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotoIdDto(
    val id: String? = "",
    @SerialName("created_at")
    val createdAt: String? = "",
    val width: Int? = 0,
    val height: Int? = 0,
    val color: String? = "",
    @SerialName("blur_hash")
    val blurHash: String? = "",
    val description: String? = "",
    @SerialName("alt_description")
    val altDescription: String? = "",
    val urls: UrlsDto? = UrlsDto(),
    val links: LinksDto? = LinksDto(),
    val user: User? = User(),
    val exif: ExifDto? = ExifDto(),
    val location: LocationDto? = LocationDto(),
    val tags: List<TagDto?>? = listOf(),
    @SerialName("related_collections")
    val relatedCollections: RelatedCollections? = RelatedCollections()
) {
    @Serializable
    data class User(
        val id: String? = "",
        val username: String? = "",
        val name: String? = "",
        @SerialName("portfolio_url")
        val portfolioUrl: String? = "",
        val bio: String? = "",
        val location: String? = "",
        val links: Links? = Links(),
        @SerialName("profile_image")
        val profileImage: ProfileImage? = ProfileImage()
    ) {
        @Serializable
        data class Links(
            val html: String? = "",
            val photos: String? = "",
            val portfolio: String? = ""
        )

        @Serializable
        data class ProfileImage(
            val small: String? = "",
            val medium: String? = "",
            val large: String? = ""
        )
    }

    @Serializable
    data class RelatedCollections(
        val results: List<Result?>? = listOf()
    ) {
        @Serializable
        data class Result(
            val id: String? = "",
            val title: String? = "",
            @SerialName("total_photos")
            val totalPhotos: Int? = 0,
            @SerialName("preview_photos")
            val previewPhotos: List<PreviewPhoto?>? = listOf()
        ) {
            @Serializable
            data class PreviewPhoto(
                val urls: Urls? = Urls()
            ) {
                @Serializable
                data class Urls(
                    val raw: String? = "",
                    val full: String? = "",
                    val regular: String? = "",
                    val small: String? = "",
                    val thumb: String? = "",
                    @SerialName("small_s3")
                    val smallS3: String? = ""
                )
            }
        }
    }
}