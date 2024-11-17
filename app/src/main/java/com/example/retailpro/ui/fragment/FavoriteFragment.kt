package com.example.retailpro.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.retailpro.R
import com.example.retailpro.adapter.FavoriteAdapter
import com.example.retailpro.adapter.HomeAdapter
import com.example.retailpro.databinding.FragmentFavoriteBinding
import com.example.retailpro.viewmodel.FavoriteViewModel

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {
    private val binding by viewBinding(FragmentFavoriteBinding::bind)
    private val favoriteViewModel by lazy {
        ViewModelProvider(requireActivity())[FavoriteViewModel::class.java]
    }
    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupObserver()
        getFavProduct()
    }

    override fun onResume() {
        super.onResume()
        getFavProduct()
    }

    private fun getFavProduct() {
        favoriteViewModel.getFavProduct()
    }

    private fun setupAdapter() {
        favoriteAdapter = FavoriteAdapter(listOf())
        binding.rvFavorite.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFavorite.adapter = favoriteAdapter
    }

    private fun setupObserver() {
        favoriteViewModel.favProduct.observe(viewLifecycleOwner) {
            favoriteAdapter.updateProduct(it)
        }
        if (favoriteViewModel.favProduct.value.isNullOrEmpty()) {
            binding.rvFavorite.visibility = View.GONE
            binding.tvEmpty.visibility = View.VISIBLE
        } else {
            binding.rvFavorite.visibility = View.VISIBLE
            binding.tvEmpty.visibility = View.GONE
        }

    }

}