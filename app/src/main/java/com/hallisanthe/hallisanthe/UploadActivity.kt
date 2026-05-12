package com.hallisanthe.hallisanthe

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.hallisanthe.hallisanthe.databinding.ActivityUploadBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class UploadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadBinding
    private var imageUri: Uri? = null
    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupCategoryDropdown()
        setupImagePicker()
        setupUploadButton()
    }

    private fun setupCategoryDropdown() {
        val categories = resources.getStringArray(R.array.categories_array)
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, categories)
        binding.spinnerCategory.setAdapter(adapter)
    }

    private fun setupImagePicker() {
        val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                imageUri = uri
                binding.imgPreview.setImageURI(uri)
                binding.imgPreview.visibility = View.VISIBLE
                binding.layoutPlaceholder.visibility = View.GONE
            }
        }

        binding.cardPickImage.setOnClickListener {
            pickImage.launch("image/*")
        }
    }

    private fun setupUploadButton() {
        binding.btnUpload.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val artisan = binding.etArtisan.text.toString().trim()
            val village = binding.etVillage.text.toString().trim()
            val priceStr = binding.etPrice.text.toString().trim()
            val category = binding.spinnerCategory.text.toString()
            val description = binding.etDescription.text.toString().trim()

            if (validateInput(name, artisan, village, priceStr, category)) {
                uploadToFirebase(name, artisan, village, priceStr.toLong(), category, description)
            }
        }
    }

    private fun validateInput(name: String, artisan: String, village: String, price: String, category: String): Boolean {
        if (name.isEmpty()) {
            binding.etName.error = "Name is required"
            return false
        }
        if (price.isEmpty()) {
            binding.etPrice.error = "Price is required"
            return false
        }
        if (imageUri == null) {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun uploadToFirebase(
        name: String, 
        artisan: String, 
        village: String, 
        price: Long, 
        category: String, 
        description: String
    ) {
        val fileName = UUID.randomUUID().toString()
        val imageRef = storage.child("product_images/$fileName")

        binding.progressIndicator.visibility = View.VISIBLE
        binding.progressIndicator.isIndeterminate = true
        binding.btnUpload.isEnabled = false

        imageUri?.let { uri ->
            imageRef.putFile(uri).addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener { downloadUrl ->

                    val product = Product(
                        name = name,
                        artisanName = artisan,
                        villageName = village,
                        price = price,
                        imageUrl = downloadUrl.toString(),
                        category = category,
                        description = description
                    )

                    db.collection("products")
                        .add(product)
                        .addOnSuccessListener {
                            binding.progressIndicator.visibility = View.GONE
                            Toast.makeText(this, "Listed in Marketplace!", Toast.LENGTH_LONG).show()
                            finish()
                        }
                        .addOnFailureListener { e ->
                            binding.progressIndicator.visibility = View.GONE
                            binding.btnUpload.isEnabled = true
                            Toast.makeText(this, "Database Error: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                }
            }.addOnFailureListener { e ->
                binding.progressIndicator.visibility = View.GONE
                binding.btnUpload.isEnabled = true
                Toast.makeText(this, "Upload Failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}