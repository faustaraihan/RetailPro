package com.example.retailpro.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.retailpro.R
import com.example.retailpro.data.local.entity.Product
import com.example.retailpro.databinding.ActivityDetailBinding
import com.example.retailpro.util.ProductUtil
import com.example.retailpro.viewmodel.DetailViewModel

class DetailActivity : AppCompatActivity(R.layout.activity_detail) {
    private val binding by viewBinding(ActivityDetailBinding::bind)
    private lateinit var detailViewModel: DetailViewModel
    private var isSaved = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
        setupButton()
        getDetail()
        setupObserver()
        isProductSaved()
    }

    private fun setupViewModel() {
        detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]
    }

    private fun setupButton() {
        binding.toolbarDetail.backButton.setOnClickListener {
            finish()
        }
        binding.favorite.setOnClickListener {
            if(!isSaved) {
                binding.favorite.setImageResource(R.drawable.ic_favorite)
                insertProduct()
            } else {
                binding.favorite.setImageResource(R.drawable.ic_unfavorite)
                deleteProduct()
            }
            isSaved = !isSaved
        }
    }

    private fun getDetail() {
        detailViewModel.getProductDetail(ProductUtil.productId)
    }

    private fun isProductSaved() {
        detailViewModel.isProductSaved(ProductUtil.productId)
    }

    private fun setupObserver() {
        detailViewModel.productDetail.observe(this) {
            Glide.with(this)
                .load(it.image)
                .centerCrop()
                .into(binding.productImage)

            binding.productTitle.text = it.title
            binding.productPrice.text = "$" + it.price.toString()
            binding.productCategory.text = it.category.capitalizeWords()
            binding.productRating.text = "Rating: " + it.rating.rate.toString()
            binding.productDescription.text = it.description
            ProductUtil.productDetail = it
        }

        detailViewModel.isSaved.observe(this) {
            isSaved = it
            if (it) {
                binding.favorite.setImageResource(R.drawable.ic_favorite)
            } else {
                binding.favorite.setImageResource(R.drawable.ic_unfavorite)
            }
        }

    }

    fun String.capitalizeWords(): String {
        return split(" ").joinToString(" ") { word ->
            word.replaceFirstChar { it.uppercaseChar() }
        }
    }

    private fun insertProduct() {
        if (ProductUtil.productDetail != null) {
            detailViewModel.insertProduct(
                Product(
                    ProductUtil.productDetail!!.id,
                    ProductUtil.productDetail!!.title,
                    ProductUtil.productDetail!!.price,
                    ProductUtil.productDetail!!.description,
                    ProductUtil.productDetail!!.category,
                    ProductUtil.productDetail!!.image,
                    ProductUtil.productDetail!!.rating
                )
            )
        }
        Toast.makeText(this, "Product added to favorite", Toast.LENGTH_SHORT).show()
    }

    private fun deleteProduct() {
        detailViewModel.deleteProduct(ProductUtil.productId)
        Toast.makeText(this, "Product removed from favorite", Toast.LENGTH_SHORT).show()
    }
}