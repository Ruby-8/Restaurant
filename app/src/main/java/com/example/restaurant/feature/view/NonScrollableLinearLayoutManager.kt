package com.example.restaurant.feature.view

import android.content.Context
import android.util.DisplayMetrics
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView


class NonScrollableLinearLayoutManager(private val context: Context) :
    LinearLayoutManager(context, RecyclerView.HORIZONTAL, false) {
    override fun canScrollHorizontally(): Boolean {
        return true
    }

    override fun canScrollVertically(): Boolean {
        return false
    }

    override fun smoothScrollToPosition(recyclerView: RecyclerView?, state: RecyclerView.State?, position: Int) {
        val smoothScroller = object : LinearSmoothScroller(context) {
            override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                return MILLISECONDS_PER_INCH / displayMetrics.densityDpi
            }
        }
        smoothScroller.targetPosition = position
        startSmoothScroll(smoothScroller)
    }

    companion object {
        private const val MILLISECONDS_PER_INCH = 50f //adjust this value as per your requirement
    }
}
