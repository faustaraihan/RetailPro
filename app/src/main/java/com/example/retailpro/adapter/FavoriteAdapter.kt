package com.example.retailpro.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.retailpro.R
import com.example.retailpro.databinding.ItemHomeCardBinding
import com.example.retailpro.ui.activity.DetailActivity
import com.example.retailpro.util.ProductUtil

class FavoriteAdapter (private var retailProduct: List<com.example.retailpro.data.local.entity.Product>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ItemHomeCardBinding.inflate(inflater, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun getItemCount(): Int = retailProduct.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as FavoriteViewHolder).bind(retailProduct[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateProduct(newItems: List<com.example.retailpro.data.local.entity.Product>) {
        retailProduct = newItems
        notifyDataSetChanged()
    }

    fun String.capitalizeWords(): String {
        return split(" ").joinToString(" ") { word ->
            word.replaceFirstChar { it.uppercaseChar() }
        }
    }

    inner class FavoriteViewHolder(
        private val itemBinding: ItemHomeCardBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(product: com.example.retailpro.data.local.entity.Product) {
            Glide.with(itemBinding.root)
                .load(product.image)
                .placeholder(R.drawable.ic_launcher_background)
                .into(itemBinding.productImage)
            itemBinding.productTitle.text = product.title
            itemBinding.productPrice.text = "$" + product.price.toString()
            itemBinding.productCategory.text = product.category.capitalizeWords()

            itemBinding.cardProduct.setOnClickListener {
                ProductUtil.productId = product.id
                itemView.context.startActivity(Intent(itemView.context, DetailActivity::class.java))
            }
        }
    }

}

