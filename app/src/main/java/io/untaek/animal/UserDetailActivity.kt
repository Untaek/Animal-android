package io.untaek.animal

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import io.untaek.animal.R.id.*
import io.untaek.animal.component.UserDetailRecyclerViewAdapter
import io.untaek.animal.firebase.Fire
import io.untaek.animal.firebase.UserDetail
import io.untaek.animal.list.UserDetailRecyclerViewAdapter
import io.untaek.animal.util.Viewer
import kotlinx.android.synthetic.main.activity_timeline_detail.*
import kotlinx.android.synthetic.main.activity_user_detail.*

class UserDetailActivity : AppCompatActivity() {

    private lateinit var user : UserDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        Log.e("ㅋㅋㅋ", "이동완료 !")
        intent.getSerializableExtra("data")?.let { d ->
            user = d as UserDetail

            var userName = textView_userName_user_detail
            val userImage = imageView_userImage_user_detail
            val postCount  = textView_postsCount_user_detail
            val followCount = textView_FollowersCount_user_detail
            val likeCount = textView_likesCount_user_detail

            Log.e("ㅋㅋㅋ", "user name : "+user.userName)
            Log.e("ㅋㅋㅋ", "user imgUrl : "+user.pictureUrl)
            Log.e("ㅋㅋㅋ", "user totalPosts : "+user.totalPosts)
            Log.e("ㅋㅋㅋ", "user totalLikes : "+user.totalLikes)
            Log.e("ㅋㅋㅋ", "user totalFollows : "+user.totalFollows)

            userName.text = user.userName
            Glide.with(this).load(Uri.parse(user.pictureUrl).toString()).into(userImage)
            postCount.text = user.totalPosts.toString()
            followCount.text= user.totalFollows.toString()
            likeCount.text = user.totalLikes.toString()

            recyclerView_user_detail.layoutManager = GridLayoutManager(this, 4)
            recyclerView_user_detail.adapter = UserDetailRecyclerViewAdapter(this, user)

            button_go_back_user_detail_detail.setOnClickListener { finish() }
            textView_followersLabel_user_detail.setOnClickListener {
                Toast.makeText(this, "클릭", Toast.LENGTH_SHORT).show()
                Fire.getInstance().follow("dbsdlswp", "untaek")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //viewer.release()
    }
}
