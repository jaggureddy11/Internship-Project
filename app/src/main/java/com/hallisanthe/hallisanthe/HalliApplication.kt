package com.hallisanthe.hallisanthe

import android.app.Application
import com.hallisanthe.hallisanthe.data.local.AppDatabase

class HalliApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}