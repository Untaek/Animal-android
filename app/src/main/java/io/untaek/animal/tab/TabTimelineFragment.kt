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
import io.untaek.animal.R
import io.untaek.animal.list.ScrollUpdateListener
import io.untaek.animal.list.TimelineAdapter
import io.untaek.animal.list.TimelineDecorator
import io.untaek.animal.list.TimelineLayoutManager
import kotlinx.android.synthetic.main.tab_timeline.view.*

const val RC_SIGN_IN = 123

class TabTimelineFragment: Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TimelineAdapter
    private lateinit var layoutManager: TimelineLayoutManager
    private lateinit var decorator: TimelineDecorator
    private lateinit var scrollUpdateListener: ScrollUpdateListener

    private var state: Parcelable? = null
    private var count = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.tab_timeline, container, false)
    }

    override fun onViewCreated(root: View, savedInstanceState: Bundle?) {
        Log.d("TABTIMELINE", "${count++}")
        recyclerView = root.recyclerView_timeline
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(decorator)
        recyclerView.addOnScrollListener(scrollUpdateListener)
        recyclerView.setHasFixedSize(true)
        recyclerView.recycledViewPool.setMaxRecycledViews(0, 30)
//        recyclerView.setItemViewCacheSize(30)
        recyclerView.isDrawingCacheEnabled = true
        recyclerView.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("TABTIMELINE", "onCreate")

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        adapter = TimelineAdapter(context!!)
        layoutManager = TimelineLayoutManager(activity, LinearLayoutManager::VERTICAL.get(), false)
        layoutManager.setExtraLayoutSpace(activity?.windowManager?.defaultDisplay?.height!!)
        decorator = TimelineDecorator()
        scrollUpdateListener = ScrollUpdateListener(adapter, adapter.getItems()) {
            adapter.updateList()
        }
        adapter.updateList()
    }

    override fun onPause() {
        super.onPause()
        state = recyclerView.layoutManager?.onSaveInstanceState()
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