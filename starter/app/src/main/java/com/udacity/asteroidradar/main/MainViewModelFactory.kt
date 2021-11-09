package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * The class is used to initialize a view model.
 * */
class MainViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    /**
     * Create a new instance of the given class.
     * */
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown viewmodel class when creating main VM.")
    }
}