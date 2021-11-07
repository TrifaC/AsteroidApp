package com.udacity.asteroidradar.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.udacity.asteroidradar.api.dto.NetworkAsteroidContainer
import com.udacity.asteroidradar.utils.Constants
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

/**
 * A public interface that exposes the [getAsteroidsListAsync] method
 */
interface AsteroidAPIService {
    /**
     * Returns a Retrofit callback that delivers a String
     * The @GET annotation indicates that the "realestate" endpoint will be requested with the GET
     * HTTP method
     */
    @GET(Constants.FEED_PATH)
    suspend fun getAsteroidsListAsync (
        @Query("start_date") startDate: String = getDatePairString().first,
        @Query("end_date") endDate: String = getDatePairString().second,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): String
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object AsteroidAPI {
    /**
     * The client is used to extend the time of fetching the Asteroid Data.
     * */
    private val client = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)

    /**
     * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
     * object pointing to the desired URL
     */
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(Constants.BASE_URL)
        .client(client.build())
        .build()
    val retrofitService:
 AsteroidAPIService = retrofit.create(AsteroidAPIService::class.java)
}

