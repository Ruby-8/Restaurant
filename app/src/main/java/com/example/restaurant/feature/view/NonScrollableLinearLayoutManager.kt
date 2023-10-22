package com.example.restaurant.feature.view

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class NonScrollableLinearLayoutManager(context: Context) :
    LinearLayoutManager(context, RecyclerView.HORIZONTAL, false) {
    override fun canScrollHorizontally(): Boolean {
        return false
    }

    override fun canScrollVertically(): Boolean {
        return false
    }
}
