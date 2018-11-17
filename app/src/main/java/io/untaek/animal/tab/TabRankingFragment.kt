package io.untaek.animal.tab

import android.os.Bundle

import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.untaek.animal.R
import io.untaek.animal.component.MyCallBack
import io.untaek.animal.component.MyOnScrollListener
import io.untaek.animal.component.TabRankingRecyclerViewAdapter
import io.untaek.animal.firebase.Post
import io.untaek.animal.firebase.UserDetail
import io.untaek.animal.firebase.dummy
import kotlinx.android.synthetic.main.tab_rank.view.*


class TabRankingFragment: Fragment(), MyCallBack {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TabRankingRecyclerViewAdapter
    private lateinit var posts : ArrayList<Post>
    private lateinit var users : ArrayList<UserDetail>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.tab_rank, container, false)
        posts = dummy.postList
        users = dummy.usersDetail
        recyclerView = view.recyclerView_tab_rank
        recyclerView.layoutManager = GridLayoutManager(view.context, 1)
        adapter = TabRankingRecyclerViewAdapter(view.context, posts, users)
        recyclerView.adapter = adapter
        recyclerView.addOnScrollListener(MyOnScrollListener(adapter, users, this))

        return view
    }

    companion object {
        fun instance(): TabRankingFragment {
            return TabRankingFragment()
        }
    }
    override fun callback() {
        adapter.update()
    }

}