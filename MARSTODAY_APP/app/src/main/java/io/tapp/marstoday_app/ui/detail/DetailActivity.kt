package io.tapp.marstoday_app.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import io.tapp.marstoday_app.R
import io.tapp.marstoday_app.repository.model.MarstodayResponse
import io.tapp.marstoday_app.repository.network.MarstodayService
import io.tapp.marstoday_app.utils.ApiKey
import io.tapp.marstoday_app.utils.CustomViewModelFactory
import kotlinx.android.synthetic.main.activity_detail.*
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class DetailActivity : AppCompatActivity() {


    private var mMarstodayResponse: MarstodayResponse? = null
    private var itemSelected = 0;

    private val mViewModel: DetailViewModel by lazy {
        val factory = CustomViewModelFactory(application)
        ViewModelProvider(this, factory).get(DetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(findViewById(R.id.toolbar))

        val sdf = SimpleDateFormat("2015-M-dd")
        val earthDate = sdf.format(Date())

        mViewModel.getMarstoday(earthDate, ApiKey.API_KEY, object : MarstodayService.CallbackResponse<MarstodayResponse> {
            override fun onResponse(response: MarstodayResponse) {

                mMarstodayResponse = response

                val numPhotos = response.photos?.size?.minus(1);
                // "https://mars.jpl.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/00786/soas/rdr/ccam/CR0_467268689PRC_F0440036CCAM15025L1.PNG";//"https://apod.nasa.gov/apod/image/2010/STScI_NGC2525_955x1024.jpg";

                numPhotos?.let {
                    itemSelected = (0..numPhotos).random()
                }

                var photoOfTheDay = response.photos?.get(itemSelected)?.imgSrc?.substring(4);
                photoOfTheDay = "https" + photoOfTheDay;


                Glide.with(this@DetailActivity)
                        .load(photoOfTheDay)
                        //.load(response.photos?.get(itemSelected)?.imgSrc)
                        .into(imageMarstodayDetail)
            }

            override fun onFailure(t: Throwable, res: Response<*>?) {
            }

        })

        btnSaveMarstodayDetail.setOnClickListener {
            mViewModel.insertMarstoday(mMarstodayResponse!!, itemSelected)
            finish()
        }
    }

}