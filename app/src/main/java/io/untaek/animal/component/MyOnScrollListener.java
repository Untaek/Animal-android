package io.untaek.animal.component;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import io.untaek.animal.firebase.Comment;
import io.untaek.animal.firebase.Comment2;
import io.untaek.animal.firebase.Post;

public class MyOnScrollListener extends RecyclerView.OnScrollListener {
    RecyclerView.Adapter adapter;
    ArrayList<?> list;
    MyCallBack callBack;

    public MyOnScrollListener(RecyclerView.Adapter adapter, ArrayList<?> list, MyCallBack callBack) {
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
