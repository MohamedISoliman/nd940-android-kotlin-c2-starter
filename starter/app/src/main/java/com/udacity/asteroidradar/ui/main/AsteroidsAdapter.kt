package com.udacity.asteroidradar.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.entities.Asteroid
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.android.synthetic.main.item_asteroid.view.*

/**
 *
 * Created by Mohamed Ibrahim on 3/18/20.
 */

class AsteroidsAdapter(private val onItemClicked: (Asteroid) -> Unit) :
    AppBaseAdapter<AsteroidViewHolder, Asteroid>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_asteroid, parent, false)
        return AsteroidViewHolder(view)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val asteroid = data[position]
        holder.bind(asteroid)
        holder.itemView.setOnClickListener { onItemClicked(asteroid) }
    }


}

class AsteroidViewHolder(viewItem: View) : RecyclerView.ViewHolder(viewItem) {

    fun bind(asteroid: Asteroid) {
        itemView.asteroidTitle.text = asteroid.codename
        itemView.asteroidDate.text = asteroid.date
        itemView.imageView.setColorFilter(
            ContextCompat.getColor(
                itemView.context,
                if (asteroid.isPotentiallyHazardous) R.color.colorAccent else android.R.color.white
            ), android.graphics.PorterDuff.Mode.MULTIPLY
        );
    }

}

abstract class AppBaseAdapter<VH : RecyclerView.ViewHolder, T> : RecyclerView.Adapter<VH>() {

    val data = ArrayList<T>()

    fun setData(data: List<T>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    protected fun getDataItem(position: Int): T {
        return data[position]
    }
}