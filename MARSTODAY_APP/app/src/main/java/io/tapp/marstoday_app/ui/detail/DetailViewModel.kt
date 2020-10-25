package io.tapp.marstoday_app.ui.detail

import android.app.Activity
import android.app.Application
import android.content.Context
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import io.tapp.marstoday_app.repository.db.MarstodayRoomDatabase
import io.tapp.marstoday_app.repository.model.MarstodayResponse
import io.tapp.marstoday_app.repository.model.PhotosItem
import io.tapp.marstoday_app.repository.network.MarstodayApi
import io.tapp.marstoday_app.repository.network.MarstodayService
import kotlinx.android.synthetic.main.activity_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val context: Application) : ViewModel() {

    fun getMarstoday(earthDate: String, apiKey: String, cb: MarstodayService.CallbackResponse<MarstodayResponse>) {

        MarstodayService().marstodayApi.getMarstoday(earthDate, apiKey).enqueue(object : Callback<MarstodayResponse> {
            override fun onResponse(call: Call<MarstodayResponse>, response: Response<MarstodayResponse>) {

                if(response.isSuccessful && response.body() != null) {
                    cb.onResponse(response.body()!!)
                } else {
                    cb.onFailure(Throwable(response.message()), response)
                }

            }

            override fun onFailure(call: Call<MarstodayResponse>, t: Throwable) {
                cb.onFailure(t)
            }

        })

    }

    fun insertMarstoday(marstodayResponse: MarstodayResponse, itemSelected: Number) {

        marstodayResponse.photos?.get(itemSelected.toInt())?.let {
            MarstodayRoomDatabase.getInstance(context).marstodayDao().insertMarstoday(it)
        };
    }

    fun deleteMarstoday(photosItem: PhotosItem) {
        MarstodayRoomDatabase.getInstance(context).marstodayDao().deleteMarstoday(photosItem)
    }
}