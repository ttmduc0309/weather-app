package com.example.weather_app.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_app.Model.HourlyView
import com.example.weather_app.R

class HourlyAdapter(private val mList: ArrayList<HourlyView>) : RecyclerView.Adapter<HourlyAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_report_unselected, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = mList[position]

        // sets the image to the imageview from our itemHolder class
        holder.hourView.text = item.hour

        // sets the text to the textview from our itemHolder class
        holder.tempView.text = item.temp.toString()

        holder.imageView.setImageResource(item.imageId)

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val hourView: TextView = itemView.findViewById(R.id.hour_id)
        val tempView: TextView = itemView.findViewById(R.id.temp_id)
        val imageView: ImageView = itemView.findViewById(R.id.hourly_image)
    }
}

