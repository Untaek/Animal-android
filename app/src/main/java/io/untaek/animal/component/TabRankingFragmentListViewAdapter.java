package io.untaek.animal.component;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import io.untaek.animal.R;
import io.untaek.animal.UserDetailActivity;

public class TabRankingFragmentListViewAdapter extends ArrayAdapter {
    Context context;
    List<TabRankingFragmentListViewItem> itemList;
    TabRankingRecyclerAdapter tabRankingRecyclerAdapter;

    public TabRankingFragmentListViewAdapter(@NonNull Context context, List<TabRankingFragmentListViewItem> itemList) {
        super(context, R.layout.component_user_detail_listview_item, itemList);
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.component_tab_rank_listview_item, parent, false);
        }
        final TabRankingFragmentListViewItem item = itemList.get(position);

        final TextView userName = convertView.findViewById(R.id.tab_rank_listview_item_user_name);

        userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UserDetailActivity.class);
                intent.putExtra("userId", item.getUserId());
                context.startActivity(intent);
            }
        });
        RecyclerView recyclerView = convertView.findViewById(R.id.tab_rank_listview_item_recycler);

        userName.setText(item.getUserName());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        tabRankingRecyclerAdapter = new TabRankingRecyclerAdapter(getContext(),item.getPostList());
        recyclerView.setAdapter(tabRankingRecyclerAdapter);

        return convertView;
    }
}