package io.untaek.animal.tab

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.app.Fragment
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import im.ene.toro.CacheManager
import im.ene.toro.PlayerSelector
import im.ene.toro.widget.Container
import io.untaek.animal.R
import io.untaek.animal.list.ScrollUpdateListener
import io.untaek.animal.list.TimelineAdapter
import io.untaek.animal.list.TimelineDecorator
import io.untaek.animal.list.TimelineLayoutManager
import kotlinx.android.synthetic.main.tab_timeline.*
import kotlinx.android.synthetic.main.tab_timeline.view.*

const val RC_SIGN_IN = 123

class TabTimelineFragment: Fragment() {
    private lateinit var timelineadapter: TimelineAdapter
    private lateinit var timelinelayoutManager: TimelineLayoutManager
    private lateinit var decorator: TimelineDecorator
    private lateinit var scrollUpdateListener: ScrollUpdateListener


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        timelineadapter = TimelineAdapter(context!!)
        timelinelayoutManager = TimelineLayoutManager(activity, LinearLayoutManager::VERTICAL.get(), false)
        timelinelayoutManager.setExtraLayoutSpace(activity?.windowManager?.defaultDisplay?.height!!)
        decorator = TimelineDecorator()
        scrollUpdateListener = ScrollUpdateListener(timelineadapter, timelineadapter.getItems()) {
            timelineadapter.updateList()
        }
        timelineadapter.updateList()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.tab_timeline, container, false)
    }

    override fun onViewCreated(root: View, savedInstanceState: Bundle?) {
        root.recyclerView_container.apply {
            adapter = timelineadapter
            layoutManager = timelinelayoutManager
            cacheManager = CacheManager.DEFAULT
            addOnScrollListener(scrollUpdateListener)
            playerSelector = PlayerSelector.DEFAULT
            isDrawingCacheEnabled = true
            drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var THIS: TabTimelineFragment? = null
        fun instance(): TabTimelineFragment {
            if(THIS == null) THIS = TabTimelineFragment()
            return THIS!!
        }
    }
}