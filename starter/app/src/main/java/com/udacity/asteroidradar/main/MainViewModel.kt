package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.api.AsteroidAPI
import com.udacity.asteroidradar.api.getDatePairString
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.data.Asteroid
import com.udacity.asteroidradar.utils.AsteroidAPIStatus
import com.udacity.asteroidradar.utils.Constants
import kotlinx.coroutines.launch
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

    // The status of asteroid request
    private val _apiStatus = MutableLiveData<AsteroidAPIStatus>()
    val apiStatus: LiveData<AsteroidAPIStatus>
        get() = _apiStatus


//------------------------------------- Init Block -------------------------------------------------


    init {
        getAsteroidFeedProperties()
    }


//------------------------------------- Network Functions ------------------------------------------


    /**
     * Method to get data from NASA, the success and failure response functions are defined.
     * */
    private fun getAsteroidFeedProperties() {
        viewModelScope.launch {
            _apiStatus.value = AsteroidAPIStatus.LOADING
            try {
                val listResult = AsteroidAPI.retrofitService.getProperties()
                val tmpJSONObject: JSONObject = JSONObject(listResult)
                _responseAsteroidList.value = parseAsteroidsJsonResult(tmpJSONObject)
                _apiStatus.value = AsteroidAPIStatus.DONE
            } catch (e: Exception) {
                _responseAsteroidList.value = null
                _apiStatus.value = AsteroidAPIStatus.ERROR
            }
        }
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