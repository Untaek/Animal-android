package io.untaek.animal.component;

import android.support.v7.widget.RecyclerView;

import java.util.List;

import io.untaek.animal.firebase.PostInTimeline;

public class TabRankingFragmentListViewItem {
    String userName;
    String userId;
    List<PostInTimeline> postList;

    public TabRankingFragmentListViewItem(String userName, String userId, List<PostInTimeline> postList) {
        this.userName = userName;
        this.userId = userId;
        this.postList = postList;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserId() {return userId;}
    public void setUserId(String userId) {this.userId = userId;}
    public List<PostInTimeline> getPostList() {
        return postList;
    }
    public void setPostList(List<PostInTimeline> postList) {
        this.postList = postList;
    }

}
