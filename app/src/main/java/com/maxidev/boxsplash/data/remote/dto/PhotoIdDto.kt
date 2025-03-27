package com.maxidev.boxsplash.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotoIdDto(
    val id: String? = "",
    val slug: String? = "",
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
    val urls: Urls? = Urls(),
    val links: Links? = Links(),
    val user: User? = User(),
    val exif: Exif? = Exif(),
    val location: Location? = Location(),
    val tags: List<Tag?>? = listOf(),
    @SerialName("related_collections")
    val relatedCollections: RelatedCollections? = RelatedCollections()
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

    @Serializable
    data class Links(
        val html: String? = "",
        val download: String? = ""
    )

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
    data class Exif(
        val make: String? = "",
        val model: String? = "",
        val name: String? = "",
        @SerialName("exposure_time")
        val exposureTime: String? = "",
        val aperture: String? = "",
        @SerialName("focal_length")
        val focalLength: String? = "",
        val iso: Int? = 0
    )

    @Serializable
    data class Location(
        val name: String? = "",
        val city: String? = "",
        val country: String? = "",
        val position: Position? = Position()
    )
        @Serializable
        data class Position(
            val latitude: Double? = 0.0,
            val longitude: Double? = 0.0
        )
    }

    @Serializable
    data class Tag(
        val title: String? = ""
    )

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