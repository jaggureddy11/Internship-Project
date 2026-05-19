package com.hallisanthe.hallisanthe.data

import com.google.firebase.firestore.FirebaseFirestore
import com.hallisanthe.hallisanthe.Product
import com.hallisanthe.hallisanthe.data.local.WishlistDao
import com.hallisanthe.hallisanthe.data.local.WishlistEntity
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map

class ProductRepository(private val wishlistDao: WishlistDao? = null) {
    private val db = FirebaseFirestore.getInstance()

    fun getProducts(): Flow<List<Product>> = callbackFlow {
        val subscription = db.collection("products").addSnapshotListener { value, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            val products = value?.documents?.mapNotNull { doc ->
                doc.toObject(Product::class.java)?.copy(id = doc.id)
            } ?: emptyList()
            
            val finalProducts = if (products.isEmpty()) {
                MockDataProvider.getMockProducts()
            } else {
                products
            }
            trySend(finalProducts)
        }
        awaitClose { subscription.remove() }
    }

    fun getWishlist(): Flow<List<Product>> = wishlistDao?.getAllWishlistItems()?.map { entities ->
        entities.map { it.toProduct() }
    } ?: callbackFlow { trySend(emptyList()) }

    suspend fun toggleWishlist(product: Product, isWishlisted: Boolean) {
        if (isWishlisted) {
            wishlistDao?.removeFromWishlist(product.toEntity())
        } else {
            wishlistDao?.addToWishlist(product.toEntity())
        }
    }

    fun isProductWishlisted(productId: String): Flow<Boolean> = 
        wishlistDao?.isWishlisted(productId) ?: callbackFlow { trySend(false) }

    private fun WishlistEntity.toProduct() = Product(id, name, price, imageUrl, category, artisanName, villageName, description)
    private fun Product.toEntity() = WishlistEntity(id, name, price, imageUrl, artisanName, villageName, description, category)
}
