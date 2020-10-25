package io.tapp.marstoday_app.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.tapp.marstoday_app.R
import io.tapp.marstoday_app.repository.model.PhotosItem
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.item_list.view.*

class MainAdapter(private val context: Context, private val callbackItemClick: CallbackItemClick, private val marstodayList: List<PhotosItem>?) : RecyclerView.Adapter<MainAdapter.MainHolder>() {

    class MainHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal var view = v
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return MainHolder(view)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        marstodayList?.get(position).let { marstoday ->

            var photoOfTheDay = marstoday?.imgSrc?.substring(4);
            photoOfTheDay = "https" + photoOfTheDay;


            Glide.with(context)
                .load(photoOfTheDay)
                //.load(response.photos?.get(itemSelected)?.imgSrc)
                .into(holder.view.imageCardView)

            holder.view.cardView.setOnClickListener {
                callbackItemClick.onItemClick(marstoday!!)
            }
        }

    }

    override fun getItemCount(): Int {
            return marstodayList!!.size
    }

}