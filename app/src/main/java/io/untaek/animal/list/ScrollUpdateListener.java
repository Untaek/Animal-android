package io.untaek.animal.list;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class ScrollUpdateListener extends RecyclerView.OnScrollListener {
    RecyclerView.Adapter adapter;
    ArrayList<?> list;
    ScrollUpdateCallback callBack;

    public ScrollUpdateListener(RecyclerView.Adapter adapter, ArrayList<?> list, ScrollUpdateCallback callBack) {
        this.adapter = adapter;
        this.list = list;
        this.callBack = callBack;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
        if(linearLayoutManager.findLastVisibleItemPosition() > list.size()-3){
            //update
            callBack.callback();
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
    }
}
