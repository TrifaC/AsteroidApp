package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.data.Asteroid

/**
 * The View Model to store data in main fragment.
 * */
class MainViewModel : ViewModel() {

    // The test list for asteroids.
    private val _testAsteroidList = MutableLiveData<ArrayList<Asteroid>>()
    val testAsteroidList: LiveData<ArrayList<Asteroid>>
        get() = _testAsteroidList

    init {
        _testAsteroidList.value = initTestAsteroidList()
    }

    override fun onCleared() {
        super.onCleared()
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