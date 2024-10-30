package com.example.herbs

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

// Adapter for displaying a list of herbs in a RecyclerView
class ListHerbsAdapter(private val listHerbs: ArrayList<Herbs>) :
    RecyclerView.Adapter<ListHerbsAdapter.ListViewHolder>() {

    // ViewHolder class to hold the views for each item
    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo) // Image view for the herb photo
        val tvName: TextView = itemView.findViewById(R.id.tv_item_name)      // Text view for the herb name
        val tvDescription: TextView = itemView.findViewById(R.id.tv_item_description) // Text view for the herb description
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        // Inflate the item layout for each herb
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_herbs, parent, false)
        return ListViewHolder(view) // Return the ViewHolder
    }

    // Return the size of the list (invoked by the layout manager)
    override fun getItemCount(): Int = listHerbs.size

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val herbs = listHerbs[position] // Get the herb at the current position

        // Load the herb photo using Glide
        Glide.with(holder.itemView.context)
            .load(herbs.photo)
            .into(holder.imgPhoto)

        // Set the herb name and description
        holder.tvName.text = herbs.name
        holder.tvDescription.text = herbs.description

        // Set click listener for the item view
        holder.itemView.setOnClickListener {
            // Create an Intent to open the DetailHerbsActivity
            val intent = Intent(holder.itemView.context, DetailHerbsActivity::class.java).apply {
                putExtra("key_herb", herbs) // Pass the Herb object
            }
            holder.itemView.context.startActivity(intent) // Start the DetailHerbsActivity
        }
    }
}
