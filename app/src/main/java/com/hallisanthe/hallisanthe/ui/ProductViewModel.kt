package com.hallisanthe.hallisanthe.ui

import android.util.Log
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

/**
 * Shared ViewModel responsible for managing product data and wishlist state.
 * It follows the UDF (Unidirectional Data Flow) pattern by exposing StateFlows.
 *
 * @param repository The repository that provides data from cloud and local sources.
 */
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

    /**
     * Triggers the fetching of products from the repository.
     */
    private fun fetchProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getProducts()
                .catch { e ->
                    Log.e("ProductViewModel", "Error fetching products", e)
                    _isLoading.value = false
                }
                .collect { productList ->
                    _products.value = productList
                    _isLoading.value = false
                }
        }
    }

    /**
     * Observes the local wishlist database for changes.
     */
    private fun fetchWishlist() {
        viewModelScope.launch {
            repository.getWishlist()
                .catch { e -> Log.e("ProductViewModel", "Error fetching wishlist", e) }
                .collect { _wishlist.value = it }
        }
    }

    /**
     * Adds or removes a product from the local wishlist.
     */
    fun toggleWishlist(product: Product) {
        viewModelScope.launch {
            try {
                val isCurrentlyWishlisted = _wishlist.value.any { it.id == product.id }
                repository.toggleWishlist(product, isCurrentlyWishlisted)
            } catch (e: Exception) {
                Log.e("ProductViewModel", "Error toggling wishlist", e)
            }
        }
    }

    /**
     * Returns a Flow that emits true if the product is wishlisted.
     */
    fun isWishlisted(productId: String) = repository.isProductWishlisted(productId)

    companion object {
        /**
         * Factory to provide the repository to the ViewModel since it needs a non-empty constructor.
         */
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
