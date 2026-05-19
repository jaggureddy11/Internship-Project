package com.hallisanthe.hallisanthe.utils

import android.view.View

/**
 * Extension function to toggle visibility between VISIBLE and GONE.
 */
fun View.setVisible(isVisible: Boolean) {
    this.visibility = if (isVisible) View.VISIBLE else View.GONE
}
