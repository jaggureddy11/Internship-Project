package com.hallisanthe.hallisanthe.data

import com.google.firebase.firestore.FirebaseFirestore
import com.hallisanthe.hallisanthe.Product
import com.hallisanthe.hallisanthe.data.local.WishlistDao
import com.hallisanthe.hallisanthe.data.local.WishlistEntity
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map

/**
 * Repository class that abstracts access to multiple data sources.
 * It manages both cloud data (Firebase Firestore) and local data (Room Database).
 *
 * @property wishlistDao Data Access Object for local wishlist operations.
 */
class ProductRepository(private val wishlistDao: WishlistDao? = null) {
    private val db = FirebaseFirestore.getInstance()

    /**
     * Fetches products from Firebase Firestore with real-time updates.
     * If the cloud database is empty, it returns a list of mock products.
     *
     * @return A Flow of a list of Product objects.
     */
    fun getProducts(): Flow<List<Product>> = callbackFlow {
        val subscription = db.collection("products").addSnapshotListener { value, error ->
            if (error != null) {
                // Better error propagation could be done here (e.g., using a Result wrapper)
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

    /**
     * Retrieves all items saved in the local wishlist.
     * Maps local entities to domain Product objects.
     */
    fun getWishlist(): Flow<List<Product>> = wishlistDao?.getAllWishlistItems()?.map { entities ->
        entities.map { it.toProduct() }
    } ?: callbackFlow { trySend(emptyList()) }

    /**
     * Toggles the wishlist status of a product.
     *
     * @param product The product to add or remove.
     * @param isWishlisted Current wishlist status.
     */
    suspend fun toggleWishlist(product: Product, isWishlisted: Boolean) {
        if (isWishlisted) {
            wishlistDao?.removeFromWishlist(product.toEntity())
        } else {
            wishlistDao?.addToWishlist(product.toEntity())
        }
    }

    /**
     * Checks if a specific product is in the local wishlist.
     */
    fun isProductWishlisted(productId: String): Flow<Boolean> = 
        wishlistDao?.isWishlisted(productId) ?: callbackFlow { trySend(false) }

    // Mappers to keep the domain model separate from the database entity
    private fun WishlistEntity.toProduct() = Product(id, name, price, imageUrl, category, artisanName, villageName, description)
    private fun Product.toEntity() = WishlistEntity(id, name, price, imageUrl, artisanName, villageName, description, category)
}
