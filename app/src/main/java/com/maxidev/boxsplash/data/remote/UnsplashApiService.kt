package com.maxidev.boxsplash.data.remote

import com.maxidev.boxsplash.data.remote.dto.CollectionsDto
import com.maxidev.boxsplash.data.remote.dto.PhotoIdDto
import com.maxidev.boxsplash.data.remote.dto.PhotosDto
import com.maxidev.boxsplash.data.remote.dto.SearchDto
import com.maxidev.boxsplash.data.remote.dto.TopicsDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UnsplashApiService {

    // Photos end points
    @GET(PHOTOS)
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<PhotosDto>

    @GET(PHOTOS + PHOTO_ID)
    suspend fun getPhotoId(@Path("id") id: String): PhotoIdDto


    // Collections end points
    @GET(COLLECTIONS)
    suspend fun getCollections(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<CollectionsDto>

    @GET(COLLECTIONS + COLLECTION_ID)
    suspend fun getCollectionId(@Path("id") id: String)

    @GET(COLLECTIONS + COLLECTION_PHOTOS)
    suspend fun getCollectionPhotos(
        @Path("id") id: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        // Valid values: landscape, portrait, squarish.
        @Query("orientation") orientation: String
    )

    @GET(COLLECTIONS + COLLECTION_ID + COLLECTION_RELATED)
    suspend fun getCollectionsRelated(@Path("id") id: String)

    // Topics end points
    @GET(TOPICS)
    suspend fun getTopics(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        // Valid values: featured, latest, oldest, position; default: position.
        @Query("order_by") orderBy: String
    ): List<TopicsDto>

    @GET(TOPICS + TOPIC_ID)
    suspend fun getTopicId(@Path("id_or_slug") idOrSlug: String)

    @GET(TOPICS + TOPIC_PHOTOS)
    suspend fun getTopicPhotos(
        @Path("id_or_slug") idOrSlug: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        // Valid values: landscape, portrait, squarish.
        @Query("orientation") orientation: String,
        // Valid values: featured, latest, oldest, position; default: position.
        @Query("order_by") orderBy: String
    )

    // Search end points
    @GET(SEARCH_PHOTOS)
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        // Valid values: landscape, portrait, squarish.
        @Query("orientation") orientation: String? = null,
        // Valid values are: black_and_white, black, white, yellow, orange, red,
        // purple, magenta, green, teal, and blue.
        @Query("color") color: String? = null,
        // Valid values: featured, latest, oldest, position; default: position.
        @Query("order_by") orderBy: String = "position"
    ): SearchDto

    @GET(SEARCH_COLLECTIONS)
    suspend fun getSearchCollections(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    )
}

private const val PHOTOS = "photos"
private const val PHOTO_ID = "/{id}"
private const val SEARCH_PHOTOS = "search/photos"
private const val SEARCH_COLLECTIONS = "search/collections"
private const val COLLECTIONS = "collections"
private const val COLLECTION_ID = "/{id}"
private const val COLLECTION_PHOTOS = "/{id}/photos"
private const val COLLECTION_RELATED = "/related"
private const val TOPICS = "topics"
private const val TOPIC_ID = "/{id_or_slug}"
private const val TOPIC_PHOTOS = "/{id_or_slug}/photos"