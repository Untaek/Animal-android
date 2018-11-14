package io.untaek.animal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import io.untaek.animal.component.UserDetailListViewAdapter;
import io.untaek.animal.component.UserDetailListViewItem;
import io.untaek.animal.firebase.PostInTimeline;
import io.untaek.animal.firebase.UserDetail;
import io.untaek.animal.firebase.dummy;

public class UserDetailActivity extends AppCompatActivity {
    FirebaseFirestore db;
    DocumentReference docRef;


    List<List<PostInTimeline>> userPost = null;
    UserDetail user;
    String uploaderId = null;
    int userPosition = 0;

    List<UserDetailListViewItem> itemList;

    public UserDetailActivity() {
        db = FirebaseFirestore.getInstance();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        DocumentSnapshot documentSnapshot =(DocumentSnapshot)getIntent().getSerializableExtra("documentSnapshot");

        //uploaderId = getIntent().getExtras().getString("uploaderId");
        uploaderId = "dbsdlswp";

//        docRef = db.collection("users").document(uploaderId);
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if(task.isSuccessful()){
//                    DocumentSnapshot document = task.getResult();
//                    textViewSetText(document);
//                    Log.d("ㅋㅋㅋ", "Cached document data : "+document.getData());
//
//                }else{
//                    Log.d("ㅋㅋㅋ","Cached get failed : "+task.getException());
//                }
//            }
//        });

        textViewSetText(documentSnapshot);

//        userPosition = 0;
//
//        for(UserDetail user : dummy.INSTANCE.getUsersDetail()){
//            if(user.getId().equals(uploaderId)){
//                break;
//            }
//            userPosition++;
//        }

        // itemlist 초기화 함수
//        defineItemList(dummy.INSTANCE.getUsersPost().get(userPosition));
//        user = dummy.INSTANCE.getUsersDetail().get(userPosition);


//        textViewSetText();
//        ListView listView = findViewById(R.id.user_detail_listView);
//
//        UserDetailListViewAdapter userDetailListViewAdapter = new UserDetailListViewAdapter(this, itemList);
//        listView.setAdapter(userDetailListViewAdapter);
    }

    List<UserDetailListViewItem> defineItemList(List<List<PostInTimeline>> user){
        itemList = new ArrayList<UserDetailListViewItem>();
        UserDetailListViewItem item;

        for(int i = 0; i<user.size(); i++){
            // petName을 이용하여 해당 pet의 게시글 가져오기
            item = new UserDetailListViewItem(user.get(i).get(0).getPetName(),user.get(i));
            itemList.add(item);
        }
        return  itemList;
    }

    private void textViewSetText(DocumentSnapshot documentSnapshot){
        TextView userName = findViewById(R.id.user_detail_user_name);
        ImageView userImage = findViewById(R.id.user_detail_user_image);
        TextView postCount = findViewById(R.id.user_detail_post_count);
        TextView followCount = findViewById(R.id.user_detail_follow_count);
        TextView likeCount = findViewById(R.id.user_detail_likes_count);


        userName.setText(documentSnapshot.get("name").toString());
        userImage.setImageURI(Uri.parse(documentSnapshot.get("photoURL").toString()));
        postCount.setText(documentSnapshot.get("total_posts").toString());
        followCount.setText(documentSnapshot.get("total_followers").toString());
        likeCount.setText(documentSnapshot.get("total_likes").toString());
    }

}
