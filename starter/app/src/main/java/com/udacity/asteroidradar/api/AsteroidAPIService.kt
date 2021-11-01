package com.udacity.asteroidradar.api

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create
import retrofit2.http.GET

private const val BASE_URL = "https://api.nasa.gov/"
private const val API_KEY = "TnzN4s3EaWSja1w0KpYs5LfuKT1h8KkhxPz4Xgve"
private const val TEST_QUERY = "neo/rest/v1/feed?start_date=2021-09-07&end_date=2021-09-13&api_key=TnzN4s3EaWSja1w0KpYs5LfuKT1h8KkhxPz4Xgve"

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
    @GET(TEST_QUERY)
    fun getProperties(): Call<String>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object AsteroidAPI {
    val retrofitService : AsteroidAPIService by lazy { retrofit.create(AsteroidAPIService::class.java) }
}

