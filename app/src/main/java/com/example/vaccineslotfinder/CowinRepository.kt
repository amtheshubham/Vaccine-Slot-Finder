package com.example.vaccineslotfinder

import retrofit2.Call
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CowinRepository @Inject constructor(private val unsplashApi: CowinApi) {

    fun getSearchResults(pincode: String, date: String): Call<CowinResponse> {

        return unsplashApi.searchPhotos(pincode, date)

    }

}