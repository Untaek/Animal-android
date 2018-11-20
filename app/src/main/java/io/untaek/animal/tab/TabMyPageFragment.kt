package io.untaek.animal.tab

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v4.app.Fragment
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.shapes.Shape
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import io.untaek.animal.PreferenceActivity
import io.untaek.animal.R
import io.untaek.animal.firebase.Fire
import io.untaek.animal.firebase.USERS
import io.untaek.animal.firebase.User
import io.untaek.animal.firebase.UserDetail
import kotlinx.android.synthetic.main.tab_my_page.*
import kotlinx.android.synthetic.main.tab_my_page.view.*

class TabMyPageFragment: Fragment() {

    private val TAG = "TabMyPageFragment"

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, "onActivityResult $requestCode")
    }

    private lateinit var user: User

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.tab_my_page, container, false)
    }

    override fun onViewCreated(root: View, savedInstanceState: Bundle?) {
        val imageView = root.imageView

        imageView.background = ShapeDrawable(OvalShape())
        imageView.clipToOutline = true

        user = Fire.Auth.getInstance().user()

        Glide.with(activity!!).load(user.pictureUrl).into(root.imageView)

        root.textView6.text = user.name

        root.logout.setOnClickListener {
            Intent(requireContext(), PreferenceActivity::class.java).apply {
                requireActivity().startActivityForResult(this, 66)
            }
        }

        FirebaseFirestore.getInstance()
                .collection(USERS)
                .document(user.id)
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        Log.w(TAG, "Listen failed.", e)
                    }

                    if (snapshot != null && snapshot.exists()) {
                        Log.d(TAG, "Current data: " + snapshot.data)
                        val userDetail = snapshot.toObject(UserDetail::class.java)
                        textView_likes.text = userDetail?.totalLikes.toString()
                        textView_posts.text = userDetail?.totalPosts.toString()
                        textView_follows.text = userDetail?.totalFollows.toString()
                    } else {
                        Log.d(TAG, "Current data: null")
                    }
                }
    }

    override fun onResume() {
        Log.d("fragment resumed", "TabMyPageFragment")
        super.onResume()
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var THIS: TabMyPageFragment? = null
        fun instance(): TabMyPageFragment {
            if(THIS == null) {
                THIS = TabMyPageFragment()
            }
            return THIS!!
        }
    }
}