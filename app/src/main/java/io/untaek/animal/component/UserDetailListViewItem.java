package io.untaek.animal.component;

import java.util.List;

import io.untaek.animal.firebase.PostInTimeline;

public class UserDetailListViewItem {
    private String petName;
    private List<PostInTimeline> postList;

    public UserDetailListViewItem(String petName, List<PostInTimeline> postList) {
        this.petName = petName;
        this.postList = postList;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public List<PostInTimeline> getPostList() {
        return postList;
    }

    public void setPostList(List<PostInTimeline> postList) {
        this.postList = postList;
    }
}
