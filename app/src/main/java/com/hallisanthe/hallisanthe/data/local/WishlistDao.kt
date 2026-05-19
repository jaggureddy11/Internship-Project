package com.hallisanthe.hallisanthe.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WishlistDao {
    @Query("SELECT * FROM wishlist")
    fun getAllWishlistItems(): Flow<List<WishlistEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToWishlist(product: WishlistEntity)

    @Delete
    suspend fun removeFromWishlist(product: WishlistEntity)

    @Query("SELECT EXISTS(SELECT * FROM wishlist WHERE id = :productId)")
    fun isWishlisted(productId: String): Flow<Boolean>
}