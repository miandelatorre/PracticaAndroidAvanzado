package io.tapp.marstoday_app.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.tapp.marstoday_app.repository.db.MarstodayRoomDatabase
import io.tapp.marstoday_app.repository.model.MarstodayResponse
import io.tapp.marstoday_app.repository.model.PhotosItem

class MainFragmentViewModel(private val context: Application) : ViewModel() {

    fun getLocalAllMarstoday() : LiveData<List<PhotosItem>> {

        return MarstodayRoomDatabase.getInstance(context).marstodayDao().getAllMarstoday()

    }
}