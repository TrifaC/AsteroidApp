package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.data.Asteroid
import com.udacity.asteroidradar.databinding.ListItemAsteroidBinding

/**
 * Adapter class to binding the data with the list item.
 *
 * @property clickListener The click listener make the list item clickable.
 * @return a [ListAdapter] connect the Asteroid data and View Holder with Diff Call Back.
 * */
class AsteroidAdapter(val clickListener: AsteroidItemClickListener) :
    ListAdapter<Asteroid, AsteroidAdapter.AsteroidViewHolder>(AsteroidDiffCallback()) {

    /**
     * Function Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to represent an item.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new Asteroid ViewHolder that holds a View of the given asteroid data.
     * */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        return AsteroidViewHolder.from(parent)
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should update the contents of the [RecyclerView.ViewHolder.itemView] to reflect the item at the given position.
     *
     * @param holderAsteroid  The ViewHolder which should be updated to represent the contents of the Asteroid item at the given position in the data set.
     * @param position The position of the Asteroid item within the adapter's data set.
     * */
    override fun onBindViewHolder(holderAsteroid: AsteroidViewHolder, position: Int) {
        holderAsteroid.bind(clickListener,getItem(position))
    }

    /**
     * The class defines the custom View Holder for asteroid item.
     * Add the private constructor means no one can create a custom ViewHolder via using this class.
     * Add the from function means only the from function can give the trigger a Fixed View Holder.
     *
     * @property binding The data binding between list item and view holder.
     * */
    class AsteroidViewHolder private constructor(private val binding: ListItemAsteroidBinding) :
        RecyclerView.ViewHolder(binding.root) {
        /**
         * Function to bind Asteroid data and View holder
         *
         * @param clickListener will be bind into the viewholder
         * @param item the Asteroid to be shown in the list item.
         * */
        fun bind(clickListener: AsteroidItemClickListener, item: Asteroid) {
            binding.asteroid = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            /**
             * Public function to give a Asteroid View Holder which prevents the custom view holder construction.
             *
             * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
             * @return A new Asteroid View Holder.
             * */
            fun from(parent: ViewGroup): AsteroidViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemAsteroidBinding.inflate(layoutInflater, parent, false)
                return AsteroidViewHolder(binding)
            }
        }
    }
}

/**
 * Class to return DiffUtil call back to make the list can be modified with higher efficiency.
 * */
class AsteroidDiffCallback : DiffUtil.ItemCallback<Asteroid>() {
    override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem == newItem
    }
}

/**
 * The click listener class to give click function for list view.
 * */
class AsteroidItemClickListener (val clickListener: (asteroid: Asteroid) -> Unit) {
    fun onClick(asteroid: Asteroid) = clickListener(asteroid)
}