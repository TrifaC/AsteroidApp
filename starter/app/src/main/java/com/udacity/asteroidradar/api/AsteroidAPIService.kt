package com.udacity.asteroidradar.api

import com.udacity.asteroidradar.utils.Constants
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create
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
 * A public interface that exposes the [getProperties] method
 */
interface AsteroidAPIService {
    /**
     * Returns a Retrofit callback that delivers a String
     * The @GET annotation indicates that the "realestate" endpoint will be requested with the GET
     * HTTP method
     */
    @GET(Constants.FEED_PATH)
    fun getProperties(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Call<String>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object AsteroidAPI {
    val retrofitService : AsteroidAPIService by lazy { retrofit.create(AsteroidAPIService::class.java) }
}

