package io.untaek.animal.tab

import android.annotation.SuppressLint
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
import io.untaek.animal.R
import io.untaek.animal.firebase.Fire
import kotlinx.android.synthetic.main.tab_my_page.view.*

class TabMyPageFragment: Fragment() {

    private val TAG = "TabMyPageFragment"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.tab_my_page, container, false)
    }

    override fun onViewCreated(root: View, savedInstanceState: Bundle?) {
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

        FirebaseFirestore.getInstance()
                .collection("user")
                .document(user?.uid!!)
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        Log.w(TAG, "Listen failed.", e)
                    }

                    if (snapshot != null && snapshot.exists()) {
                        Log.d(TAG, "Current data: " + snapshot.getData())
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