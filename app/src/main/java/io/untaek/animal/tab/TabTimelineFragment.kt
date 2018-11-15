package io.untaek.animal.tab

import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.untaek.animal.R
import io.untaek.animal.component.TimelineAdapter
import io.untaek.animal.component.TimelineDecorator
import io.untaek.animal.component.TimelineLayoutManager
import kotlinx.android.synthetic.main.tab_timeline.view.*

const val RC_SIGN_IN = 123

class TabTimelineFragment: Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TimelineAdapter
    private lateinit var layoutManager: TimelineLayoutManager
    private lateinit var decorator: TimelineDecorator

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.tab_timeline, container, false)

        recyclerView = root.recyclerView_timeline
        adapter = TimelineAdapter(context!!)
        layoutManager = TimelineLayoutManager(activity, LinearLayoutManager::VERTICAL.get(), false)
        layoutManager.setExtraLayoutSpace(activity?.windowManager?.defaultDisplay?.height!!)
        decorator = TimelineDecorator()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(decorator)
        recyclerView.setHasFixedSize(true)
        recyclerView.recycledViewPool.setMaxRecycledViews(0, 30)
        recyclerView.setItemViewCacheSize(30)
        recyclerView.isDrawingCacheEnabled = true
        recyclerView.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH

        adapter.updateList()

        return root
    }

    companion object {
        fun instance(): TabTimelineFragment {
            return TabTimelineFragment()
        }
    }
}