package io.tapp.marstoday_app.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide.init
import io.tapp.marstoday_app.R
import io.tapp.marstoday_app.repository.model.MarstodayResponse
import io.tapp.marstoday_app.repository.model.PhotosItem
import io.tapp.marstoday_app.ui.detail.DetailActivity
import io.tapp.marstoday_app.ui.detail.DetailActivity.Companion.LOCAL_MARSTODAY
import io.tapp.marstoday_app.ui.detail.DetailActivity.Companion.OBJECT_MARSTODAY
import io.tapp.marstoday_app.ui.detail.DetailViewModel
import io.tapp.marstoday_app.utils.CustomViewModelFactory
import kotlinx.android.synthetic.main.fragment_main.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MainFragment : Fragment(), CallbackItemClick {

    companion object {
        const val TAG = "MainFragment"
        fun newInstance() = MainFragment().apply {  }
    }

    private var mAdapter: MainAdapter? = null
    private var marstodayList: List<PhotosItem>? = null


    private val mViewModel: MainFragmentViewModel by lazy {
        val factory = CustomViewModelFactory(activity!!.application)
        ViewModelProvider(this, factory).get(MainFragmentViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        getLocalAllMarstoday()

    }

    private fun init() {
        recyclerViewMainList.layoutManager = LinearLayoutManager(activity)
        recyclerViewMainList.isNestedScrollingEnabled = false
        recyclerViewMainList.setHasFixedSize(false)
    }

    private fun getLocalAllMarstoday() {

        mViewModel.getLocalAllMarstoday().observe(viewLifecycleOwner, Observer { marstodayList ->

            mAdapter = MainAdapter(activity!!.applicationContext, this, marstodayList)
            recyclerViewMainList.adapter = mAdapter
        })
    }

    override fun onItemClick(photosItem: PhotosItem) {
        activity?.let { fragment ->
            Intent(fragment, DetailActivity::class.java).apply {

                arguments = Bundle().apply {
                    putSerializable(OBJECT_MARSTODAY, photosItem)
                }

                arguments?.let { args -> putExtras(args) }

                putExtra("EXTRA_MARSTODAY", LOCAL_MARSTODAY)
                startActivity(this)
            }
        }
    }
}