package com.udacity.asteroidradar.api

import com.udacity.asteroidradar.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface NASAImageOfDayAPIService {
    @GET(Constants.IMAGE_PATH)
    suspend fun getImageInfo (
        @Query("api_key") apiKey: String = Constants.API_KEY
    )

    object NASAImageOfDayAPI {

    }

}