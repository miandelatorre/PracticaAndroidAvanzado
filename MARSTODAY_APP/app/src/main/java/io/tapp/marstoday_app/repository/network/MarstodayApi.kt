package io.tapp.marstoday_app.repository.network

import io.tapp.marstoday_app.repository.model.MarstodayResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MarstodayApi {

    // https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?earth_date=2015-6-3&api_key=DEMO_KEY
    @GET("mars-photos/api/v1/rovers/curiosity/photos")
    @Headers("Content-Type: application/json")
    fun getMarstoday( @Query("earth_date") earthDate: String, @Query("api_key") apiKey: String): Call<MarstodayResponse>

}