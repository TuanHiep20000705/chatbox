package com.mth.example.buoi2telegram.ui

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationScrollListener(private val linearLayoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy == 0) {
            return
        }
        val totalItemCount = linearLayoutManager.itemCount
        val lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition()
        if (isLoading() || isLastPage()) {
            return
        }
        if (LOAD_MORE_THRESHOLD + lastVisibleItemPosition > totalItemCount) {
            loadMoreItem()
        }
    }

    abstract fun loadMoreItem()
    abstract fun isLoading(): Boolean
    abstract fun isLastPage(): Boolean
    companion object {
        private const val LOAD_MORE_THRESHOLD = 3
    }
}
