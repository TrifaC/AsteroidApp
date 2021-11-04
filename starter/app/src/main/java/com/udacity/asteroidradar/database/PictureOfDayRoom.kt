package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PictureOfDayDao {
    /**
     * The method to get picture entity from database.
     * */
    @Query("select * from databasepictureofdayentity")
    fun getPictureOfDay(): LiveData<DatabasePictureOfDayEntity>

    /**
     * The method to update picture entity from database.
     * */


}