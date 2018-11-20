package io.untaek.animal.list

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet

class TimelineLayoutManager : LinearLayoutManager {
    private val DEFAULT_EXTRA_LAYOUT_SPACE = 600
    private var extraLayoutSpace = -1

    constructor(context: Context): super(context)
    constructor(context: Context?, orientation: Int, reverseLayout: Boolean) : super(context, orientation, reverseLayout)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    fun setExtraLayoutSpace(extraSpace: Int) {
        extraLayoutSpace = extraSpace
    }

    override fun getExtraLayoutSpace(state: RecyclerView.State?): Int {
        if(extraLayoutSpace > 0) {
            return extraLayoutSpace
        }
        return DEFAULT_EXTRA_LAYOUT_SPACE
    }
}