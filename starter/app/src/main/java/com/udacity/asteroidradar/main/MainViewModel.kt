package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.api.NASAImageOfDayAPI
import com.udacity.asteroidradar.data.Asteroid
import com.udacity.asteroidradar.data.PictureOfDay
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.repository.AsteroidsRepository
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * The View Model to store data in main fragment.
 * */
class MainViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
        private const val LOG_TAG: String = "MainViewModel"
    }

    private val database = getDatabase(application)
    private val asteroidsRepository = AsteroidsRepository(database)

    // The navigation action to detail page.
    private val _navigateToDetail = MutableLiveData<Asteroid?>()
    val navigateToDetail: LiveData<Asteroid?>
        get() = _navigateToDetail

    // The picture of today to show in the banner.
    private val _pictureOfTodayEntity = MutableLiveData<PictureOfDay?>()
    val pictureOfDay: LiveData<PictureOfDay?>
        get() = _pictureOfTodayEntity




//------------------------------------- Init Block -------------------------------------------------

    init {
        getAppDataProperty()
    }
    val responseAsteroidList = asteroidsRepository.asteroids


//------------------------------------- Image Update Function --------------------------------------


    fun getAppDataProperty() {
        viewModelScope.launch {
            try {
                asteroidsRepository.refreshAsteroid()
                _pictureOfTodayEntity.value = NASAImageOfDayAPI.retrofitService.getImageInfo()
            } catch (e: Exception) { }
        }
    }


//------------------------------------- Event Trigger Functions ------------------------------------


    fun onAsteroidClicked(asteroid: Asteroid) {
        _navigateToDetail.value = asteroid
    }

    fun doneNavigation() {
        _navigateToDetail.value = null
    }


}