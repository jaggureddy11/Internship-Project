package com.hallisanthe.hallisanthe

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

/**
 * Modern ListAdapter for the Product grid.
 * Uses DiffUtil for high-performance updates and smooth animations.
 *
 * @param onClick Callback function triggered when an item is clicked.
 */
class ProductAdapter(private val onClick: (Product) -> Unit) : ListAdapter<Product, ProductAdapter.ViewHolder>(ProductDiffCallback()) {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val name: TextView = v.findViewById(R.id.txtProductName)
        val artisan: TextView = v.findViewById(R.id.txtArtisanName)
        val village: TextView = v.findViewById(R.id.txtVillageName)
        val priceTag: TextView = v.findViewById(R.id.tvPriceTag)
        val img: ImageView = v.findViewById(R.id.imgProduct)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val p = getItem(position)
        holder.name.text = p.name
        holder.artisan.text = holder.itemView.context.getString(R.string.artisan_name_label, p.artisanName)
        holder.village.text = p.villageName
        holder.priceTag.text = "₹${p.price}"

        val context = holder.itemView.context
        val color = when {
            p.price <= 299 -> ContextCompat.getColor(context, R.color.budget_green)
            p.price <= 999 -> ContextCompat.getColor(context, R.color.medium_orange)
            else -> ContextCompat.getColor(context, R.color.premium_purple)
        }
        holder.priceTag.backgroundTintList = ColorStateList.valueOf(color)

        Glide.with(context)
            .load(p.imageUrl)
            .placeholder(R.drawable.gray_placeholder)
            .error(R.drawable.gray_placeholder)
            .centerCrop()
            .into(holder.img)

        holder.itemView.setOnClickListener { onClick(p) }
    }

    /**
     * Efficiently calculates the difference between two lists.
     */
    class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
}
