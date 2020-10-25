package io.tapp.marstoday_app.utils

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.tapp.marstoday_app.ui.detail.DetailViewModel
import io.tapp.marstoday_app.ui.main.MainFragmentViewModel
import java.lang.IllegalArgumentException

class CustomViewModelFactory(private val application: Application) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>) : T {
        return with(modelClass) {
            when {
                isAssignableFrom(MainFragmentViewModel::class.java) -> MainFragmentViewModel(application)
                isAssignableFrom(DetailViewModel::class.java) -> DetailViewModel(application)
                else -> throw IllegalArgumentException("Unknown ViewModel")
            }
        } as T

    }

}