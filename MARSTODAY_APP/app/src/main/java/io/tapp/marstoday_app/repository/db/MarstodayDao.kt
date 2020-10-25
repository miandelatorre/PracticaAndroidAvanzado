package io.tapp.marstoday_app.repository.db

import androidx.lifecycle.LiveData
import androidx.room.*
import io.tapp.marstoday_app.repository.model.MarstodayResponse
import io.tapp.marstoday_app.repository.model.PhotosItem


@Dao
abstract class MarstodayDao {

    @Query("SELECT * FROM marstoday_table")
    abstract fun getAllMarstoday(): LiveData<List<PhotosItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertMarstoday(photosItem: PhotosItem)

    @Delete
    abstract fun deleteMarstoday(photosItem: PhotosItem)
}