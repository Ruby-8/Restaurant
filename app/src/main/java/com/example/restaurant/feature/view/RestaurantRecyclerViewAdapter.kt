package com.example.restaurant.feature.view

import android.view.LayoutInflater.from
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.restaurant.R
import com.example.restaurant.feature.model.Restaurant

class RestaurantRecyclerViewAdapter(private val restaurantList: List<Restaurant>):
    RecyclerView.Adapter<RestaurantRecyclerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantRecyclerViewHolder {
        val view = from(parent.context).inflate(R.layout.layout_card, parent, false)
        return RestaurantRecyclerViewHolder(view)
    }

    override fun getItemCount(): Int = restaurantList.size

    override fun onBindViewHolder(holder: RestaurantRecyclerViewHolder, position: Int) {
        holder.bind(restaurantList.get(position))
    }
}

class RestaurantRecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val _imageView: ImageView = itemView.findViewById(R.id.imageView)
    private val _name: TextView = itemView.findViewById(R.id.name)
    private val _rating: TextView = itemView.findViewById(R.id.rating)
    fun bind(restaurant: Restaurant) {
        Glide.with(itemView)
            .load(restaurant.imageUrl)
            .centerCrop()
            .into(_imageView);
        _name.text = restaurant.name
        _rating.text = "${restaurant.rating}/5.0"

    }

}