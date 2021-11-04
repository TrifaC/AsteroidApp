package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.api.dto.NetworkNASAImageContainer
import com.udacity.asteroidradar.data.PictureOfDay
import com.udacity.asteroidradar.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(Constants.BASE_URL)
    .build()

interface NASAImageOfDayAPIService {
    @GET(Constants.IMAGE_PATH)
    suspend fun getImageInfo(
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): NetworkNASAImageContainer

}

object NASAImageOfDayAPI {
    val retrofitService: NASAImageOfDayAPIService by lazy { retrofit.create(NASAImageOfDayAPIService::class.java) }
}