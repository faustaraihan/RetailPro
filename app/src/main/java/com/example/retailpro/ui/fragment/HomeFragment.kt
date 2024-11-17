package com.example.retailpro.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.retailpro.R
import com.example.retailpro.adapter.HomeAdapter
import com.example.retailpro.data.ProductRepository
import com.example.retailpro.data.local.LocalDatabase
import com.example.retailpro.data.remote.network.ApiClient
import com.example.retailpro.databinding.FragmentHomeBinding
import com.example.retailpro.viewmodel.HomeViewModel

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeAdapter: HomeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupRecyclerView()
        setupObservers()
        getProduct()
    }

    private fun setupViewModel() {
        homeViewModel = ViewModelProvider(requireActivity()) [HomeViewModel::class.java]
    }

    private fun setupRecyclerView() {
        homeAdapter = HomeAdapter(listOf())
        binding.rvHome.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHome.adapter = homeAdapter
    }

    private fun getProduct() {
        homeViewModel.getProducts()
    }

    private fun setupObservers() {
        homeViewModel.products.observe(viewLifecycleOwner) { products ->
            homeAdapter.updateProduct(products)
        }
        homeViewModel.exception.observe(viewLifecycleOwner) { error ->
            if (error) {
                Toast.makeText(requireContext(), "Tidak ada internet", Toast.LENGTH_SHORT).show()
                homeViewModel.clearException()
            }
        }
    }
}