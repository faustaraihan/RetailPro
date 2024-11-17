package com.example.retailpro.ui.activity

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.retailpro.R
import com.example.retailpro.adapter.HomeAdapter
import com.example.retailpro.databinding.ActivitySearchBinding
import com.example.retailpro.viewmodel.SearchViewModel

class SearchActivity : AppCompatActivity(R.layout.activity_search){
    private val binding by viewBinding(ActivitySearchBinding::bind)
    private val searchViewModel by lazy {
        ViewModelProvider(this)[SearchViewModel::class.java]
    }
    private lateinit var searchAdapter: HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupButton()
        setupSearchView()
        setupRecyclerView()
        setupObserver()
    }

    private fun setupButton() {
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        searchAdapter = HomeAdapter(listOf())
        binding.rvSearch.layoutManager = LinearLayoutManager(this)
        binding.rvSearch.adapter = searchAdapter
    }

    private fun setupSearchView() {
        binding.searchView.queryHint = "Search for product"
        val searchEditText =
            binding.searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        val typeface = ResourcesCompat.getFont(this, R.font.lato_bold)
        searchEditText.typeface = typeface
        searchEditText.textSize = 14f
        searchEditText.setHintTextColor(ContextCompat.getColor(this, R.color.grey1))
        searchEditText.setTextColor(ContextCompat.getColor(this, R.color.black))

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    searchViewModel.getEventsByKeyword(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun setupObserver() {
        searchViewModel.productByKeyword.observe(this) { products ->
            searchAdapter.updateProduct(newItems = products)

            if (products.isNotEmpty()) {
                binding.rvSearch.visibility = View.VISIBLE
                binding.tvNoResult.visibility = View.GONE
            } else {
                binding.rvSearch.visibility = View.GONE
                binding.tvNoResult.visibility = View.VISIBLE
            }
        }
    }

}