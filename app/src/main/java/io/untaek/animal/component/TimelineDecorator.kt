package io.untaek.animal.component

import android.graphics.Canvas
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View

class TimelineDecorator() : RecyclerView.ItemDecoration() {

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        Log.d("TimelineDecorator", "onDraw")

        repeat(parent.adapter.itemCount) {

        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        Log.d("TimelineDecorator", "getItemOffsets")
        if(parent.getChildAdapterPosition(view) != parent.adapter.itemCount - 1) {
           outRect.bottom = 30
        }
    }
}