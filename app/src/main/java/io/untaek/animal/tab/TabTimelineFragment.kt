package io.untaek.animal.tab

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.untaek.animal.R

class TabTimelineFragment: Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.tab_timeline, container, false).rootView
    }

    companion object {
        fun instance(): TabTimelineFragment {
            return TabTimelineFragment()
        }
    }
}