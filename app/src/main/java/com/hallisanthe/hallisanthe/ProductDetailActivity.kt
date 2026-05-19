package com.hallisanthe.hallisanthe

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.hallisanthe.hallisanthe.databinding.ActivityProductDetailBinding
import com.hallisanthe.hallisanthe.ui.ProductViewModel
import kotlinx.coroutines.launch

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailBinding
    private val viewModel: ProductViewModel by viewModels { ProductViewModel.Factory }
    private var currentProduct: Product? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra("product_id") ?: ""
        val name = intent.getStringExtra("product_name") ?: ""
        val price = intent.getLongExtra("product_price", 0)
        val imageUrl = intent.getStringExtra("product_image") ?: ""
        val artisan = intent.getStringExtra("product_artisan") ?: "Unknown"
        val village = intent.getStringExtra("product_village") ?: "Unknown"
        val description = intent.getStringExtra("product_description") ?: ""
        val category = intent.getStringExtra("product_category") ?: "General"

        currentProduct = Product(id, name, price, imageUrl, category, artisan, village, description)

        binding.txtDetailName.text = name
        binding.txtDetailPrice.text = "₹$price"
        binding.txtDetailArtisan.text = artisan
        binding.txtDetailVillage.text = village
        binding.txtDetailDescription.text = description

        Glide.with(this)
            .load(imageUrl)
            .into(binding.imgProductLarge)

        binding.toolbar.setNavigationOnClickListener { finish() }

        binding.btnWishlistToggle.setOnClickListener {
            currentProduct?.let { viewModel.toggleWishlist(it) }
        }

        observeWishlist(id)
    }

    private fun observeWishlist(productId: String) {
        lifecycleScope.launch {
            viewModel.isWishlisted(productId).collect { isWishlisted ->
                val icon = if (isWishlisted) {
                    android.R.drawable.btn_star_big_on
                } else {
                    android.R.drawable.btn_star_big_off
                }
                binding.btnWishlistToggle.setImageResource(icon)
            }
        }
    }
}