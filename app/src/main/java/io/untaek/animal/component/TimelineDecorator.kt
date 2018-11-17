package io.untaek.animal.component

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View

class TimelineDecorator : RecyclerView.ItemDecoration() {

    lateinit var divider: Drawable

    private val strokeWidth = 3f
    private val itemPadding = 30

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        repeat(parent.childCount) { i ->
            val bottom = parent.getChildAt(i).bottom - parent.paddingBottom

            val paint = Paint().apply {
                color = Color.YELLOW
                strokeWidth = strokeWidth
            }
            c.drawLine(left.toFloat(), bottom.toFloat(), right.toFloat(), bottom.toFloat(), paint)
            c.drawRect(left.toFloat(), bottom.toFloat() + strokeWidth, right.toFloat(), bottom.toFloat() + itemPadding, paint)
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if(parent.getChildAdapterPosition(view) != parent.adapter.itemCount - 1) {
           outRect.bottom = itemPadding
        }
    }
}