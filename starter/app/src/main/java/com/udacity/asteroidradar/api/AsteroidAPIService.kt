package com.udacity.asteroidradar.api

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.nasa.gov/"
private const val FEED_PATH = "neo/rest/v1/feed"
private const val API_KEY = "TnzN4s3EaWSja1w0KpYs5LfuKT1h8KkhxPz4Xgve"

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object pointing to the desired URL
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
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
    @GET(FEED_PATH)
    fun getProperties(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String = API_KEY
    ): Call<String>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object AsteroidAPI {
    val retrofitService : AsteroidAPIService by lazy { retrofit.create(AsteroidAPIService::class.java) }
}

