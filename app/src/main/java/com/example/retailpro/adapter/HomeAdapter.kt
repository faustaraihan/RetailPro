package com.example.retailpro.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.retailpro.R
import com.example.retailpro.data.remote.model.Product
import com.example.retailpro.databinding.ItemHomeCardBinding
import com.example.retailpro.ui.activity.DetailActivity
import com.example.retailpro.util.ProductUtil

class HomeAdapter (private var retailProduct: List<Product>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = ItemHomeCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(view)
    }

    override fun getItemCount(): Int = retailProduct.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as HomeViewHolder).bind(retailProduct[position])
    }

    fun updateProduct(newItems: List<Product>) {
        retailProduct = newItems
        notifyDataSetChanged()
    }

    fun String.capitalizeWords(): String {
        return split(" ").joinToString(" ") { word ->
            word.replaceFirstChar { it.uppercaseChar() }
        }
    }

    inner class HomeViewHolder(
        private val itemBinding: ItemHomeCardBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(product: Product) {
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

