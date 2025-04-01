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
    val tags: List<TagDto?>? = listOf()
) {
    @Serializable
    data class User(
        val id: String? = "",
        val username: String? = "",
        val name: String? = "",
        @SerialName("portfolio_url")
        val portfolioUrl: String? = "",
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
            val large: String? = ""
        )
    }
}