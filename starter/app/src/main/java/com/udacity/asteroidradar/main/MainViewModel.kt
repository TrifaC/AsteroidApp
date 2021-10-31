package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.data.Asteroid

/**
 * The View Model to store data in main fragment.
 * */
class MainViewModel : ViewModel() {
    companion object {
        private const val LOG_TAG: String = "MainViewModel"
    }

    // The test list for asteroids.
    private val _testAsteroidList = MutableLiveData<ArrayList<Asteroid>>()
    val testAsteroidList: LiveData<ArrayList<Asteroid>>
        get() = _testAsteroidList

    // The navigation action to detail page.\
    private val _navigateToDetail = MutableLiveData<Asteroid?>()
    val navigateToDetail: LiveData<Asteroid?>
        get() = _navigateToDetail

    init {
        _testAsteroidList.value = initTestAsteroidList()
    }

    override fun onCleared() {
        super.onCleared()
    }

    fun onAsteroidClicked(asteroid: Asteroid) {
        _navigateToDetail.value = asteroid
    }

    fun doneNavigation() {
        _navigateToDetail.value = null
    }


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