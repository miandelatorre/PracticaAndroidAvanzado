package io.tapp.marstoday_app.repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.tapp.marstoday_app.repository.model.PhotosItem

@Database(entities = [PhotosItem::class], version = 1, exportSchema = false)
abstract class MarstodayRoomDatabase : RoomDatabase() {

    abstract fun marstodayDao(): MarstodayDao

    companion object {

        private var INSTANCE: MarstodayRoomDatabase? = null

        fun getInstance(context: Context): MarstodayRoomDatabase {

            if (INSTANCE == null) {

                synchronized(MarstodayRoomDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, MarstodayRoomDatabase::class.java, "marstoday_db")
                            .allowMainThreadQueries()
                            .build()
                }
            }

            return INSTANCE!!
        }
    }

}