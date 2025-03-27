package com.maxidev.boxsplash.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.maxidev.boxsplash.BuildConfig
import com.maxidev.boxsplash.data.remote.UnsplashApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    private const val BASE_URL = "https://api.unsplash.com/"
    private const val CONTENT_TYPE = "application/json"
    private const val AUTHORIZATION_HEADER = "Authorization:"
    private const val CLIENT_ID_HEADER = "Client_ID"
    private const val API_KEY = BuildConfig.apiKey
    private const val TIMER_OUT = 15L

    @Provides
    @Singleton
    fun providesRetrofit(
        json: Json,
        client: OkHttpClient
    ): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(
                json.asConverterFactory(CONTENT_TYPE.toMediaType())
            )
            .build()
    }

    @Provides
    @Singleton
    fun providesJson(): Json {
        return Json {
            ignoreUnknownKeys = true
        }
    }

    @Provides
    @Singleton
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun providesOkHttp(
        interceptor: Interceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val httpBuilder = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(loggingInterceptor)
            .readTimeout(TIMER_OUT, TimeUnit.SECONDS)
            .connectTimeout(TIMER_OUT, TimeUnit.SECONDS)
            .writeTimeout(TIMER_OUT, TimeUnit.SECONDS)
            .callTimeout(TIMER_OUT, TimeUnit.SECONDS)

        return httpBuilder.build()
    }

    @Provides
    @Singleton
    fun providesInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .header("Authorization", "Client-ID $API_KEY")
                .build()

            chain.proceed(request)
        }
    }

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit): UnsplashApiService =
        retrofit.create(UnsplashApiService::class.java)
}