package io.untaek.animal

import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.database.collection.LLRBNode
import io.untaek.animal.firebase.*
import io.untaek.animal.list.UserDetailRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_user_detail.*
import java.lang.Exception

class UserDetailActivity : AppCompatActivity(), Fire.Callback<UserDetail> {
    override fun onResult(data: UserDetail ) {
        user = UserDetail(user_.id, user_.name, user_.pictureUrl, data.totalLikes, data.totalPosts, data.totalFollowers)
        setWidget()
    }

    override fun onFail(e: Exception) {

        Log.e("ㅋㅋㅋ", "=================================onFail " + e)
    }

    private lateinit var user : UserDetail
    private lateinit var user_ : User
    private var followFlag   = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        intent.getSerializableExtra("data")?.let { d ->
            user = d as UserDetail
        }
        intent.getSerializableExtra("postDetail_user")?.let { d ->
            user_ = d as User
            Fire.getInstance().getUserDetail(d.id, this)
            Log.e("ㅋㅋㅋ", "d name  : "+d.name)
            Log.e("ㅋㅋㅋ", "d picture  : "+d.pictureUrl)
            Log.e("ㅋㅋㅋ", "d id  : "+d.id)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //viewer.release()
    }

    fun setWidget(){
        var userName = textView_userName_user_detail
        val userImage = imageView_userImage_user_detail
        val postCount  = textView_postsCount_user_detail
        val followerCount = textView_FollowersCount_user_detail
        val likeCount = textView_likesCount_user_detail

        Log.e("ㅋㅋㅋ", "user name : "+user.userName)
        Log.e("ㅋㅋㅋ", "user id  : "+user.id)
        Log.e("ㅋㅋㅋ", "user userimg : "+user.pictureUrl)
        Log.e("ㅋㅋㅋ", "user totalFollowers  : "+user.totalFollowers)
        Log.e("ㅋㅋㅋ", "user total post : "+user.totalPosts)
        Log.e("ㅋㅋㅋ", "user like  : "+user.totalLikes)
        userName.text = user.userName
        userImage.clipToOutline = true
        userImage.background = ShapeDrawable(OvalShape())

        Glide.with(this).load(user.pictureUrl).into(userImage)
        postCount.text = user.totalPosts.toString()
        likeCount.text = user.totalLikes.toString()
        followerCount.text = user.totalFollowers.toString()

        recyclerView_user_detail.layoutManager = GridLayoutManager(this, 3)
        recyclerView_user_detail.adapter = UserDetailRecyclerViewAdapter(this, user).also {
            it.update()
        }

        button_go_back_user_detail_detail.setOnClickListener { finish() }
        textView_followersLabel_user_detail.setOnClickListener {
            Log.e("ㅋㅋㅋ","userDetail Activity user.id : "+user.id)
            Fire.getInstance().checkFollow("dbsdlswp",user.id, object : Fire.Callback<Boolean> {
                override fun onResult(data: Boolean) {
                    if (data == true) {
                        Log.e("ㅋㅋㅋ", "unfollow")
                        Fire.getInstance().unfollow("dbsdlswp", user.id)
                        followerCount.text = (followerCount.text.toString().toInt() - 1).toString()
                    } else {
                        Log.e("ㅋㅋㅋ", "follow")
                        Fire.getInstance().follow("dbsdlswp", user.id)
                        followerCount.text = (followerCount.text.toString().toInt() + 1).toString()
                    }
                }

                override fun onFail(e: Exception) {
                    Log.e("ㅋㅋㅋ", "check follow 실패")
                }
            })
        }
    }
}
