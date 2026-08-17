package com.uo300568.artchive.data.remote.wiki

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

@OptIn(ExperimentalSerializationApi::class)
object WikiApiClient {

    private const val BASE_URL = "https://en.wikipedia.org/api/rest_v1/"

    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }

    private val userAgentInterceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()
            .header("User-Agent", "Artchive/1.0 (artchive@example.com)")
            .build()
        chain.proceed(request)
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .addInterceptor(userAgentInterceptor)
        .build()

    val wikiApi: WikiApiService by lazy {
        val contentType = "application/json".toMediaType()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(WikiApiService::class.java)
    }
}