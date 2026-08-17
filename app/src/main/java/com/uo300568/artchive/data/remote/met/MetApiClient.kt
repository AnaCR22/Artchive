package com.uo300568.artchive.data.remote.met

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit


@OptIn(ExperimentalSerializationApi::class)
object MetApiClient {

    private const val BASE_URL = "https://collectionapi.metmuseum.org/public/collection/v1/"

    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    val metApi: MetApiService by lazy {
        val contentType = "application/json".toMediaType()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(MetApiService::class.java)
    }
}