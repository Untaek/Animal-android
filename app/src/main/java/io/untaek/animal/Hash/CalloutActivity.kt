package io.untaek.animal.Hash

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import com.bumptech.glide.Glide
import io.untaek.animal.R
import io.untaek.animal.component.UserDetailRecyclerViewAdapter
import io.untaek.animal.firebase.UserDetail
import kotlinx.android.synthetic.main.activity_callout_profile.*
import kotlinx.android.synthetic.main.activity_user_detail.*

class CalloutActivity : AppCompatActivity() {

    private lateinit var user : UserDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_callout_profile)

        Log.e("ㅋㅋㅋ", "이동완료 !")


        intent.getSerializableExtra("data")?.let { d ->
            user = d as UserDetail
            Log.d("users", user.userName)

            var userName = user_detail_user_name
            val userImage = user_detail_user_image
            val postCount  = user_detail_post_count
            val followCount = user_detail_follow_count
            val likeCount = user_detail_likes_count

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

            user_detail_listView.layoutManager = GridLayoutManager(this, 4)
            user_detail_listView.adapter = UserDetailRecyclerViewAdapter(this, user)

//            button_go_back_user_detail_detail.setOnClickListener { finish() }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //viewer.release()
    }
}
