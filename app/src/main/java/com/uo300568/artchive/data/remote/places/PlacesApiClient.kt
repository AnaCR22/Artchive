package com.uo300568.artchive.data.remote.places

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import okhttp3.MediaType.Companion.toMediaType
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory

@OptIn(ExperimentalSerializationApi::class)
object PlacesApiClient {

    private const val BASE_URL = "https://places.googleapis.com/v1/"

    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    val placesApi: PlacesApiService by lazy {
        val contentType = "application/json".toMediaType()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(PlacesApiService::class.java)
    }
}