package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.udacity.asteroidradar.api.AsteroidAPI
import com.udacity.asteroidradar.data.Asteroid
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

    // The test list for asteroids.
    private val _testAsteroidList = MutableLiveData<ArrayList<Asteroid>>()
    val testAsteroidList: LiveData<ArrayList<Asteroid>>
        get() = _testAsteroidList

    // The navigation action to detail page.
    private val _navigateToDetail = MutableLiveData<Asteroid?>()
    val navigateToDetail: LiveData<Asteroid?>
        get() = _navigateToDetail

    // The response from NASA API.
    private val _asteroidResponse = MutableLiveData<String?>()
    val asteroidResponse: LiveData<String?>
        get() = _asteroidResponse


//------------------------------------- Init Block -------------------------------------------------


    init {
        _testAsteroidList.value = initTestAsteroidList()
        getAsteroidFeedProperties()
    }


//------------------------------------- Network Functions ------------------------------------------


    /**
     * Method to get data from NASA, the success and failure response functions are defined.
     * */
    private fun getAsteroidFeedProperties() {
        AsteroidAPI.retrofitService.getProperties().enqueue( object: retrofit2.Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                _asteroidResponse.value = response.body()
                Log.d(LOG_TAG, "The result is ${_asteroidResponse.value}")
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                _asteroidResponse.value = "Failure: ${t.message}"
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


//------------------------------------- Test Function-----------------------------------------------


    private fun initTestAsteroidList(): ArrayList<Asteroid> {
        val asteroidItem1 = Asteroid(1, "Name:1.1", "Date:1.1.1", 1.0, 1.0, 1.0, 1.0, true)
        val asteroidItem2 = Asteroid(2, "Name:2.1", "Date:2.1.1", 1.0, 1.0, 1.0, 1.0, false)
        val asteroidItem3 = Asteroid(3, "Name:3.1", "Date:3.1.1", 1.0, 1.0, 1.0, 1.0, true)
        val asteroidItem4 = Asteroid(4, "Name:4.1", "Date:4.1.1", 1.0, 1.0, 1.0, 1.0, false)
        val defaultList = ArrayList<Asteroid>()
        defaultList.add(asteroidItem1)
        defaultList.add(asteroidItem2)
        defaultList.add(asteroidItem3)
        defaultList.add(asteroidItem4)
        return defaultList
    }

}