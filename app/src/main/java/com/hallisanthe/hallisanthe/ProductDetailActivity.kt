package com.hallisanthe.hallisanthe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.hallisanthe.hallisanthe.databinding.ActivityProductDetailBinding

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("product_name")
        val price = intent.getLongExtra("product_price", 0)
        val imageUrl = intent.getStringExtra("product_image")
        val artisan = intent.getStringExtra("product_artisan")
        val village = intent.getStringExtra("product_village")
        val description = intent.getStringExtra("product_description")

        binding.txtDetailName.text = name
        binding.txtDetailPrice.text = "₹$price"
        binding.txtDetailArtisan.text = artisan
        binding.txtDetailVillage.text = village
        binding.txtDetailDescription.text = description

        Glide.with(this)
            .load(imageUrl)
            .into(binding.imgProductLarge)

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}