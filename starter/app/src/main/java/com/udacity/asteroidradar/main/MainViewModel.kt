package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.udacity.asteroidradar.api.AsteroidAPI
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.data.Asteroid
import com.udacity.asteroidradar.utils.Constants
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

/**
 * The View Model to store data in main fragment.
 * */
class MainViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
        private const val LOG_TAG: String = "MainViewModel"
    }

    // The navigation action to detail page.
    private val _navigateToDetail = MutableLiveData<Asteroid?>()
    val navigateToDetail: LiveData<Asteroid?>
        get() = _navigateToDetail

    // Response asteroid list
    private val _responseAsteroidList = MutableLiveData<ArrayList<Asteroid>?>()
    val responseAsteroidList: LiveData<ArrayList<Asteroid>?>
        get() = _responseAsteroidList


//------------------------------------- Init Block -------------------------------------------------


    init {
        getAsteroidFeedProperties()
    }


//------------------------------------- Network Functions ------------------------------------------


    /**
     * Method to get data from NASA, the success and failure response functions are defined.
     * */
    private fun getAsteroidFeedProperties() {
        AsteroidAPI.retrofitService.getProperties(
            Constants.DEFAULT_TEST_START_DAY,
            Constants.DEFAULT_TEST_END_DAY
        ).enqueue(object : retrofit2.Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.d(LOG_TAG, "Get the response: ${response.body()}")
                val tmpJSONObject: JSONObject = JSONObject(response.body()!!)
                _responseAsteroidList.value = parseAsteroidsJsonResult(tmpJSONObject)
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d(LOG_TAG, "Failure: ${t.message}")
                _responseAsteroidList.value = null
            }
        })
    }


//------------------------------------- Event Trigger Functions ------------------------------------


    fun onAsteroidClicked(asteroid: Asteroid) {
        _navigateToDetail.value = asteroid
    }

    fun doneNavigation() {
        _navigateToDetail.value = null
    }


//------------------------------------- View Model Lifecycle Function ------------------------------


    override fun onCleared() {
        super.onCleared()
    }


}