package io.tapp.marstoday_app.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import io.tapp.marstoday_app.R
import io.tapp.marstoday_app.repository.model.MarstodayResponse
import io.tapp.marstoday_app.repository.model.PhotosItem
import io.tapp.marstoday_app.repository.network.MarstodayService
import io.tapp.marstoday_app.utils.ApiKey
import io.tapp.marstoday_app.utils.CustomViewModelFactory
import kotlinx.android.synthetic.main.activity_detail.*
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class DetailActivity : AppCompatActivity() {

    companion object {
        const val TAG = "DetailActivity"
        const val LOCAL_MARSTODAY = "LOCAL_MARSTODAY"
        const val OBJECT_MARSTODAY = "OBJECT_MARSTODAY"
        const val SERVER_MARSTODAY = "SERVER_MARSTODAY"
    }

    private var mMarstodayResponse: MarstodayResponse? = null
    private var mPhotosItem: PhotosItem? = null
    private var itemSelected = 0;
    private var localMarstoday = false;

    private val mViewModel: DetailViewModel by lazy {
        val factory = CustomViewModelFactory(application)
        ViewModelProvider(this, factory).get(DetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        listeners()

    }

    private fun init() {
        setContentView(R.layout.activity_detail)
        setSupportActionBar(findViewById(R.id.toolbar))

        intent?.let {
            if(it.getStringExtra("EXTRA_MARSTODAY")  == LOCAL_MARSTODAY) {


                localMarstoday = true

                btnMarstodayDetail.text = "DELETE"

                mPhotosItem = intent.extras!!.getSerializable(OBJECT_MARSTODAY) as PhotosItem?


                var photoOfTheDay = mPhotosItem?.imgSrc?.substring(4);
                photoOfTheDay = "https" + photoOfTheDay;


                Glide.with(this@DetailActivity)
                        .load(photoOfTheDay)
                        //.load(response.photos?.get(itemSelected)?.imgSrc)
                        .into(imageMarstodayDetail)

            } else {
                localMarstoday = false
                btnMarstodayDetail.text = "SAVE"
                getServerMarstoday()
            }
        }
    }

    private fun listeners() {

        btnMarstodayDetail.setOnClickListener {

            if (localMarstoday) {
                mViewModel.deleteMarstoday(mPhotosItem!!)
            } else {
                mViewModel.insertMarstoday(mMarstodayResponse!!, itemSelected)
            }

            finish()

        }
    }

    private fun getServerMarstoday() {
        val sdf = SimpleDateFormat("2016-M-dd")
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

    }

}