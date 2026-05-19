package com.hallisanthe.hallisanthe

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.hallisanthe.hallisanthe.databinding.FragmentWishlistBinding
import com.hallisanthe.hallisanthe.ui.ProductViewModel
import kotlinx.coroutines.launch

class WishlistFragment : Fragment() {

    private var _binding: FragmentWishlistBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: ProductViewModel by viewModels { ProductViewModel.Factory }
    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentWishlistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        productAdapter = ProductAdapter { product ->
            val intent = Intent(requireContext(), ProductDetailActivity::class.java).apply {
                putExtra("product_id", product.id)
                putExtra("product_name", product.name)
                putExtra("product_price", product.price)
                putExtra("product_image", product.imageUrl)
                putExtra("product_artisan", product.artisanName)
                putExtra("product_village", product.villageName)
                putExtra("product_description", product.description)
            }
            startActivity(intent)
        }
        binding.rvWishlist.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = productAdapter
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.wishlist.collect { items ->
                productAdapter.submitList(items)
                binding.emptyWishlistLayout.visibility = if (items.isEmpty()) View.VISIBLE else View.GONE
                binding.rvWishlist.visibility = if (items.isNotEmpty()) View.VISIBLE else View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}