package com.fabulouszanna.pokedex.utilities

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.fabulouszanna.pokedex.R

class RecyclerViewCustomItemDecoration :
    RecyclerView.ItemDecoration {
    private val startEndOffset: Int
    private val topBottomOffset: Int

    constructor(context: Context) {
        val offset = context.resources.getDimensionPixelOffset(R.dimen.card_margin)
        this.startEndOffset = offset
        this.topBottomOffset = offset
    }

    constructor(startEndOffset: Int, topBottomOffset: Int) {
        this.startEndOffset = startEndOffset.toPx()
        this.topBottomOffset = topBottomOffset.toPx()
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.set(startEndOffset, topBottomOffset, startEndOffset, topBottomOffset)
    }
}