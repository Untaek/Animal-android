package io.untaek.animal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.untaek.animal.component.UserDetailListViewAdapter;
import io.untaek.animal.component.UserDetailListViewItem;
import io.untaek.animal.firebase.PostInTimeline;
import io.untaek.animal.firebase.UserDetail;
import io.untaek.animal.firebase.dummy;

public class UserDetailActivity extends AppCompatActivity {

    List<List<PostInTimeline>> userPost = null;
    UserDetail user;
    boolean findFlag = false;
    String userId = null;
    int userPosition = 0;

    List<UserDetailListViewItem> itemList;

    public UserDetailActivity() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        userId = getIntent().getExtras().getString("userId");
        userPosition = 0;
        for(UserDetail user : dummy.INSTANCE.getUsersDetail()){
            if(user.getId().equals(userId)){
                break;
            }
            userPosition++;
        }

        // itemlist 초기화 함수
        defineItemList(dummy.INSTANCE.getUsersPost().get(userPosition));
        user = dummy.INSTANCE.getUsersDetail().get(userPosition);

        textViewSetText();
        ListView listView = findViewById(R.id.user_detail_listView);

        UserDetailListViewAdapter userDetailListViewAdapter = new UserDetailListViewAdapter(this, itemList);
        listView.setAdapter(userDetailListViewAdapter);
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

    private void textViewSetText(){
        TextView userName = findViewById(R.id.user_detail_user_name);
        ImageView userImage = findViewById(R.id.user_detail_user_image);
        TextView postCount = findViewById(R.id.user_detail_post_count);
        TextView followCount = findViewById(R.id.user_detail_follow_count);
        TextView likeCount = findViewById(R.id.user_detail_likes_count);


        userName.setText(user.getUserName());
        userImage.setImageResource(user.getImgResource());
        postCount.setText(""+ user.getPosts());
        followCount.setText(""+ user.getFollows());
        likeCount.setText(""+ user.getTotalLikes());
    }

}
