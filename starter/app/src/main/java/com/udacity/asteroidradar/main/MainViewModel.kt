package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.api.NASAImageOfDayAPI
import com.udacity.asteroidradar.api.getTodayDateString
import com.udacity.asteroidradar.api.isNetworkAvailable
import com.udacity.asteroidradar.data.Asteroid
import com.udacity.asteroidradar.data.AsteroidAPIFilter
import com.udacity.asteroidradar.data.PictureOfDay
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.repository.AsteroidsRepository
import com.udacity.asteroidradar.utils.Constants
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

/**
 * The View Model to store data in main fragment.
 * */
class MainViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
        private const val LOG_TAG: String = "MainViewModel"
    }

    private val database = getDatabase(application)
    private val asteroidsRepository = AsteroidsRepository(database)
    private var currentListFilter = AsteroidAPIFilter.SAVED

    // The navigation action to detail page.
    private val _navigateToDetail = MutableLiveData<Asteroid?>()
    val navigateToDetail: LiveData<Asteroid?>
        get() = _navigateToDetail

    // The picture of today to show in the banner.
    private val _pictureOfTodayEntity = MutableLiveData<PictureOfDay?>()
    val pictureOfDay: LiveData<PictureOfDay?>
        get() = _pictureOfTodayEntity

    // The string to inform user
    private val _stateInfoShowing = MutableLiveData<String?>()
    val stateInfoShowing: LiveData<String?>
        get() = _stateInfoShowing

    // The list for showing
    private val _asteroidShowingList = MutableLiveData<List<Asteroid>>()
    val asteroidShowingList: LiveData<List<Asteroid>>
        get() = _asteroidShowingList


//------------------------------------- Init Block -------------------------------------------------


    init {
        getAppDataProperty()
    }

    val responseAsteroidList = asteroidsRepository.asteroids


//------------------------------------- Image Update Function --------------------------------------


    private fun getAppDataProperty() {
        if (isNetworkAvailable(getApplication())) {
            viewModelScope.launch {
                try {
                    _stateInfoShowing.value = "Fetching Internet Data......"
                    asteroidsRepository.refreshAsteroid()
                    val tmpPictureOfDay = NASAImageOfDayAPI.retrofitService.getImageInfo()
                    if (tmpPictureOfDay.mediaType != "image") {
                        _pictureOfTodayEntity.value = PictureOfDay(
                            "image",
                            "Default NASA Image",
                            "https://apod.nasa.gov/apod/image/2001/STSCI-H-p2006a-h-1024x614.jpg"
                        )
                    } else {
                        _pictureOfTodayEntity.value =
                            NASAImageOfDayAPI.retrofitService.getImageInfo()
                    }
                } catch (e: Exception) {
                    _stateInfoShowing.value = "Error Happen In Fetching Internet Data."
                }
            }
        } else {
            _stateInfoShowing.value = "No Internet Connection."
        }
    }

    fun refreshListData(filter: AsteroidAPIFilter?) {
        if (filter != currentListFilter) {
            _stateInfoShowing.value = "Refreshing List Data......"
            filter?.let { currentListFilter = it }
            when (filter) {
                AsteroidAPIFilter.TODAY -> {
                    _asteroidShowingList.value =
                        responseAsteroidList.value!!.filter { it.closeApproachDate == getTodayDateString() }
                }
                AsteroidAPIFilter.WEEK -> {
                    _asteroidShowingList.value =
                        responseAsteroidList.value!!.filter {
                            timeString2Milli(it.closeApproachDate) > timeString2Milli(
                                getTodayDateString()
                            )
                        }
                }
                else -> {
                    _asteroidShowingList.value = responseAsteroidList.value
                }
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


//------------------------------------- Support Functions ------------------------------------------


    private fun timeString2Milli(timeString: String): Long {
        val simpleDateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT)
        val date: Date? = simpleDateFormat.parse(timeString)
        return date!!.time
    }

}