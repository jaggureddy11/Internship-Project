package com.hallisanthe.hallisanthe

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.chip.Chip
import com.hallisanthe.hallisanthe.databinding.FragmentHomeBinding
import com.hallisanthe.hallisanthe.ui.ProductViewModel
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: ProductViewModel by viewModels()
    private lateinit var productAdapter: ProductAdapter
    
    private var currentCategory: String = "All"
    private var currentQuery: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        arguments?.getString("selected_category")?.let {
            currentCategory = it
        }

        setupRecyclerView()
        setupSearchView()
        setupChips()
        observeViewModel()
        
        // Update chip selection if category was passed
        if (currentCategory != "All") {
            for (i in 0 until binding.chipGroupCategories.childCount) {
                val chip = binding.chipGroupCategories.getChildAt(i) as? Chip
                if (chip?.text?.toString()?.contains(currentCategory, ignoreCase = true) == true) {
                    chip.isChecked = true
                    break
                }
            }
        }
    }

    private fun setupRecyclerView() {
        productAdapter = ProductAdapter { product ->
            val intent = Intent(requireContext(), ProductDetailActivity::class.java).apply {
                putExtra("product_id", product.id)
                // Also pass other data to avoid extra fetch for now
                putExtra("product_name", product.name)
                putExtra("product_price", product.price)
                putExtra("product_image", product.imageUrl)
                putExtra("product_artisan", product.artisanName)
                putExtra("product_village", product.villageName)
                putExtra("product_description", product.description)
            }
            startActivity(intent)
        }
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = productAdapter
        }
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                currentQuery = query ?: ""
                applyFilters()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                currentQuery = newText ?: ""
                applyFilters()
                return true
            }
        })
    }

    private fun setupChips() {
        binding.chipGroupCategories.setOnCheckedStateChangeListener { group, checkedIds ->
            val chip = group.findViewById<Chip>(checkedIds.firstOrNull() ?: -1)
            currentCategory = chip?.text?.toString() ?: "All"
            applyFilters()
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.products.collect { _ ->
                applyFilters()
            }
        }
        
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isLoading.collect { isLoading ->
                // Show/hide progress indicator if you have one
            }
        }
    }

    private fun applyFilters() {
        val allProducts = viewModel.products.value
        
        val filtered = allProducts.filter { product ->
            val matchesCategory = if (currentCategory == "All" || currentCategory.contains("Trending")) {
                true
            } else {
                val cleanCategory = if (currentCategory.contains(" ")) currentCategory.split(" ").last() else currentCategory
                product.category.contains(cleanCategory, ignoreCase = true)
            }
            
            val matchesQuery = product.name.contains(currentQuery, ignoreCase = true) || 
                               product.description.contains(currentQuery, ignoreCase = true)
            
            matchesCategory && matchesQuery
        }

        productAdapter.submitList(filtered)
        binding.emptyStateLayout.visibility = if (filtered.isEmpty()) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}