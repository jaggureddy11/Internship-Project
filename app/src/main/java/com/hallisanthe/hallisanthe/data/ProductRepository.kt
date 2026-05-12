package com.hallisanthe.hallisanthe.data

import com.google.firebase.firestore.FirebaseFirestore
import com.hallisanthe.hallisanthe.Product
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ProductRepository {
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
}