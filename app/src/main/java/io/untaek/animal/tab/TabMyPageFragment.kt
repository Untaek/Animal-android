package io.untaek.animal.tab

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
import io.untaek.animal.R
import kotlinx.android.synthetic.main.tab_my_page.view.*

class TabMyPageFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.tab_my_page, container, false)

        val user = FirebaseAuth.getInstance().currentUser
        val imageView = root.imageView

        imageView.background = ShapeDrawable(OvalShape())
        if(Build.VERSION.SDK_INT >= 21)
            imageView.clipToOutline = true
        Glide.with(activity!!).load(user?.photoUrl).into(root.imageView)

        root.textView6.text = (FirebaseAuth.getInstance().currentUser?.displayName)

        root.logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
        }

        return root    }

    override fun onResume() {
        Log.d("fragment resumed", "TabMyPageFragment")
        super.onResume()
    }

    companion object {
        fun instance(): TabMyPageFragment {
            return TabMyPageFragment()
        }
    }
}