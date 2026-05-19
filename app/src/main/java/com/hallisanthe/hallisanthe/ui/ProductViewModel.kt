package com.hallisanthe.hallisanthe.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.hallisanthe.hallisanthe.HalliApplication
import com.hallisanthe.hallisanthe.Product
import com.hallisanthe.hallisanthe.data.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    private val _wishlist = MutableStateFlow<List<Product>>(emptyList())
    val wishlist: StateFlow<List<Product>> = _wishlist.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        fetchProducts()
        fetchWishlist()
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getProducts()
                .catch { _isLoading.value = false }
                .collect { productList ->
                    _products.value = productList
                    _isLoading.value = false
                }
        }
    }

    private fun fetchWishlist() {
        viewModelScope.launch {
            repository.getWishlist()
                .catch { /* handle error */ }
                .collect { _wishlist.value = it }
        }
    }

    fun toggleWishlist(product: Product) {
        viewModelScope.launch {
            val isCurrentlyWishlisted = _wishlist.value.any { it.id == product.id }
            repository.toggleWishlist(product, isCurrentlyWishlisted)
        }
    }

    fun isWishlisted(productId: String) = repository.isProductWishlisted(productId)

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]) as HalliApplication
                val repository = ProductRepository(application.database.wishlistDao())
                return ProductViewModel(repository) as T
            }
        }
    }
}