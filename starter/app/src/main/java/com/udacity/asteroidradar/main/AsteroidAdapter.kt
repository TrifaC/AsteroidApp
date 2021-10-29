package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.data.Asteroid

class AsteroidAdapter : RecyclerView.Adapter<AsteroidAdapter.ViewHolder>() {

    // Create custom setter and tell kotlin to save the new value.
    var asteroidListData = ArrayList<Asteroid>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    /** Function to tell recycler view how to create  the list item. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_item_asteroid, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = asteroidListData[position]
        val res = holder.itemView.context.resources
        holder.asteroidNameTV.text = item.codename
        holder.asteroidDateTV.text = item.closeApproachDate
        holder.asteroidHazardousIV.setImageResource(when(item.isPotentiallyHazardous) {
            true -> R.drawable.ic_status_potentially_hazardous
            else -> R.drawable.ic_status_normal
        })

    }

    /** Function to tell recycler view how many items will be shown in the list. */
    override fun getItemCount() = asteroidListData.size

    /**
     * The class defines the custom View Holder for asteroid item.
     *
     * @param itemView is the view of the list item.
     * */
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val asteroidNameTV: TextView = itemView.findViewById(R.id.asteroid_name_TV)
        val asteroidDateTV: TextView = itemView.findViewById(R.id.asteroid_date_TV)
        val asteroidHazardousIV: ImageView = itemView.findViewById(R.id.asteroid_is_hazardous_IV)
    }

}

