package io.untaek.animal.component;

import android.content.Context;
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

public class UserDetailListViewAdapter extends ArrayAdapter {
    Context context;
    List<UserDetailListViewItem> itemList;
    TabRankingRecyclerAdapter tabRankingRecyclerAdapter;

    public UserDetailListViewAdapter(@NonNull Context context, List<UserDetailListViewItem> itemList) {
        super(context, R.layout.component_user_detail_listview_item, itemList);
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.component_user_detail_listview_item, parent, false);
        }
        UserDetailListViewItem item = itemList.get(position);

        TextView petName = convertView.findViewById(R.id.user_detail_listviewitem_pet_name);
        RecyclerView recyclerView = convertView.findViewById(R.id.user_detail_listviewitem_recycler);

        petName.setText(item.getPetName());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        tabRankingRecyclerAdapter = new TabRankingRecyclerAdapter(getContext(),item.getPostList());

//        tabRankingRecyclerAdapter.setClickListener(new TabRankingRecyclerAdapter.ItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//
//            }
//        });
        recyclerView.setAdapter(tabRankingRecyclerAdapter);

        return convertView;
    }
}