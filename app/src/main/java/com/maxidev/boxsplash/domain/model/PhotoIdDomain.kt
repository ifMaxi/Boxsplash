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
    val tags: List<String>
) {
    data class UserDomain(
        val id: String,
        val username: String,
        val name: String,
        val portfolioUrl: String,
        val links: UserLinksDomain,
        val profileImage: ProfileImageDomain
    ) {
        data class UserLinksDomain(
            val html: String,
            val photos: String,
            val portfolio: String
        )
        data class ProfileImageDomain(
            val large: String
        )
    }
}