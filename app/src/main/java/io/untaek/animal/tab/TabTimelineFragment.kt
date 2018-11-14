package io.untaek.animal.tab

import android.support.v4.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.firestore.FirebaseFirestore
import io.untaek.animal.R
import io.untaek.animal.component.BasicTimelineAdapter
import io.untaek.animal.component.TimelineLayoutManager
import kotlinx.android.synthetic.main.tab_timeline.view.*

const val RC_SIGN_IN = 123

class TabTimelineFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val adapter = BasicTimelineAdapter(activity!!)

        val root = inflater.inflate(R.layout.tab_timeline, container, false)

        val recyclerView = root.recyclerView_timeline
        val layoutManager = TimelineLayoutManager(activity, LinearLayoutManager::VERTICAL.get(), false)
        layoutManager.setExtraLayoutSpace(activity?.windowManager?.defaultDisplay?.height!!)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
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