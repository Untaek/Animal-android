package io.untaek.animal.component;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.List;

import io.untaek.animal.R;
import io.untaek.animal.UserDetailActivity;

public class TabRankingFragmentListViewAdapter extends ArrayAdapter {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference docRef;
    Intent intent;
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
        final TextView userFollow = convertView.findViewById(R.id.tab_rank_listview_item_follow_count_text);
        final TextView userLike = convertView.findViewById(R.id.tab_rank_listview_item_like_count_text);
        RecyclerView recyclerView = convertView.findViewById(R.id.tab_rank_listview_item_recycler);
        Log.d("ㅋㅋㅋ", "Dㄴㅁㅇㄹㄴㅁㄹㅇㄴㅇㄹnapshot data: " );


        userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                docRef = db.collection("users").document("dbsdlswp");
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if (documentSnapshot.exists()) {
                                intent= new Intent(context, UserDetailActivity.class);
                                intent.putExtra("dataSnapshot", documentSnapshot.toObject(Serializable.class));
                                context.startActivity(intent);
                                Log.d("ㅋㅋㅋ", "DocumentSnapshot data: " + documentSnapshot.getData());
                            } else {
                                Log.d("ㅋㅋㅋ", "No such document");
                            }
                        } else {
                            Log.d("ㅋㅋㅋ", "get failed with ", task.getException());
                        }
                    }
                });


            }
        });
        userName.setText(item.getUserName());
        userFollow.setText("20");
        userLike.setText("5");
        //userFollow.setText(팔로우 갯수);
        //userLike.setText(좋아요 갯수)
        //item.getUserId() 로 DataBase에서 불러오기.
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        tabRankingRecyclerAdapter = new TabRankingRecyclerAdapter(getContext(),item.getPostList());
        recyclerView.setAdapter(tabRankingRecyclerAdapter);

        return convertView;
    }
}