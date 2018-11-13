package io.untaek.animal.tab;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.untaek.animal.PostDetailActivity;
import io.untaek.animal.R;
import io.untaek.animal.UserDetailActivity;
import io.untaek.animal.component.TabRankingFragmentListViewAdapter;
import io.untaek.animal.component.TabRankingFragmentListViewItem;
import io.untaek.animal.component.TabRankingGridAdapter;
import io.untaek.animal.component.TabRankingRecyclerAdapter;
import io.untaek.animal.component.UserDetailListViewItem;
import io.untaek.animal.firebase.PostInTimeline;
import io.untaek.animal.firebase.dummy;

public class TabRankingFragment_ extends Fragment {

    private static TabRankingFragment_ instance;

    List<TabRankingFragmentListViewItem> itemList;

    public static TabRankingFragment_ instance(){
        if(instance == null) {
            instance = new TabRankingFragment_();

        }
        return instance;
    }

    public TabRankingFragment_(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab_rank, container, false);

        GridView gridview = view.findViewById(R.id.tab_rank_grid_hotpost);
        ListView listView = view.findViewById(R.id.tab_rank_listView);


        gridview.setAdapter(new TabRankingGridAdapter(getContext(), dummy.INSTANCE.getGrid_posts()));


        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent intent = new Intent(getContext(), PostDetailActivity.class);
                intent.putExtra("data", dummy.INSTANCE.getGrid_posts().get(position));
                getContext().startActivity(intent);
            }
        });


        // itemlist 초기화
        defineItemList(dummy.INSTANCE.getUsersPost());

        TabRankingFragmentListViewAdapter tabRankingFragmentListViewAdapter = new TabRankingFragmentListViewAdapter(getContext(), itemList);
        listView.setAdapter(tabRankingFragmentListViewAdapter);

        return view;
    }

    List<TabRankingFragmentListViewItem> defineItemList(List<List<List<PostInTimeline>>> users){
        itemList = new ArrayList<TabRankingFragmentListViewItem>();
        TabRankingFragmentListViewItem item;
        for(int i = 0; i<users.size(); i++){
            List<PostInTimeline> postList = new ArrayList<>();
            for(int j = 0; j< users.get(i).size();j++){
                for(int k = 0; k<users.get(i).get(j).size(); k++){
                    postList.add(users.get(i).get(j).get(k));
                }
            }
            item = new TabRankingFragmentListViewItem(users.get(i).get(0).get(0).getUserName(),users.get(i).get(0).get(0).getUserId(),postList);
            itemList.add(item);
        }
        return  itemList;
    }
}
