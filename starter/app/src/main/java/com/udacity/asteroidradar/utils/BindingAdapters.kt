package com.udacity.asteroidradar.utils

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.data.AsteroidAPIStatus

/**
 * The kotlin file to contain all binding adapter function to be used in the xml file.
 * */

/**
 * Input isHazardous then showing the icon according to the value. (Main Page)
 * */
@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}

/**
 * Input isHazardous then showing the image according to the value. (Detail Page)
 * */
@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    val context = imageView.context
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
        imageView.contentDescription = context.getString(R.string.potentially_hazardous_asteroid_image)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
        imageView.contentDescription = context.getString(R.string.not_hazardous_asteroid_image)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

@BindingAdapter("asteroidAPIStatus")
fun bindAsteroidAPIStatus(textView: TextView, status: AsteroidAPIStatus) {
    when (status) {
        AsteroidAPIStatus.LOADING -> {
            textView.visibility = View.VISIBLE
            textView.text = "Loading the data."
        }
        AsteroidAPIStatus.DONE -> {
            textView.visibility = View.GONE
        }
        AsteroidAPIStatus.ERROR -> {
            textView.visibility = View.VISIBLE
            textView.text = "Something error in data fetching."
        }
    }
}
