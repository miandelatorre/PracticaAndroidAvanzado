package io.tapp.marstoday_app.ui.main

import io.tapp.marstoday_app.repository.model.MarstodayResponse
import io.tapp.marstoday_app.repository.model.PhotosItem

interface CallbackItemClick {
    fun onItemClick(photosItem: PhotosItem)
}