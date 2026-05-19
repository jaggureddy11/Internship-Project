package com.hallisanthe.hallisanthe.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wishlist")
data class WishlistEntity(
    @PrimaryKey val id: String,
    val name: String,
    val price: Long,
    val imageUrl: String,
    val artisanName: String,
    val villageName: String,
    val description: String,
    val category: String
)