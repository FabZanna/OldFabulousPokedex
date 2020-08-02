package com.fabulouszanna.pokedex.utilities

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.fabulouszanna.pokedex.R

class RecyclerViewCustomItemDecoration(private val context: Context) :
    RecyclerView.ItemDecoration() {
    private val offset = context.resources.getDimensionPixelOffset(R.dimen.card_margin)
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.set(offset, offset, offset, offset)
    }
}