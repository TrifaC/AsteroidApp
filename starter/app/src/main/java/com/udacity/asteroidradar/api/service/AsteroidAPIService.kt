package com.udacity.asteroidradar.api

import com.udacity.asteroidradar.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object pointing to the desired URL
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(Constants.BASE_URL)
    .build()

/**
 * A public interface that exposes the [getAsteroidsList] method
 */
interface AsteroidAPIService {
    /**
     * Returns a Retrofit callback that delivers a String
     * The @GET annotation indicates that the "realestate" endpoint will be requested with the GET
     * HTTP method
     */
    @GET(Constants.FEED_PATH)
    suspend fun getAsteroidsList (
        @Query("start_date") startDate: String = getDatePairString().first,
        @Query("end_date") endDate: String = getDatePairString().second,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): String
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object AsteroidAPI {
    val retrofitService : AsteroidAPIService by lazy { retrofit.create(AsteroidAPIService::class.java) }
}

