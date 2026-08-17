package com.uo300568.artchive

import android.app.Application
import com.uo300568.artchive.data.firebase.FirestoreService
import com.uo300568.artchive.data.remote.met.MetApiClient
import com.uo300568.artchive.data.remote.places.PlacesApiClient
import com.uo300568.artchive.data.remote.wiki.WikiApiClient
import com.uo300568.artchive.data.repository.CuadroRepository
import com.uo300568.artchive.data.repository.CuadroRepositoryImpl
import com.uo300568.artchive.data.repository.MuseoRepository
import com.uo300568.artchive.data.repository.MuseoRepositoryImpl
import com.uo300568.artchive.data.repository.UserRepository
import com.uo300568.artchive.data.repository.UserRepositoryImpl

class ArtchiveApp : Application() {

    lateinit var cuadroRepository: CuadroRepository
    lateinit var userRepository: UserRepository
    lateinit var museumRepository: MuseoRepository

    override fun onCreate() {
        super.onCreate()
        val firestoreService = FirestoreService()

        cuadroRepository = CuadroRepositoryImpl(
            wikiApi = WikiApiClient.wikiApi,
            apiService = MetApiClient.metApi,
            firestoreService = firestoreService
        )

        userRepository = UserRepositoryImpl(firestoreService)

        museumRepository = MuseoRepositoryImpl(
            apiService = PlacesApiClient.placesApi,
            apiKey = BuildConfig.MAPS_API_KEY
        )
    }
}