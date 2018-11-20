package io.untaek.animal

import android.content.Intent
import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import io.untaek.animal.firebase.Fire
import io.untaek.animal.tab.*
import kotlinx.android.synthetic.main.activity_tabs.*
import java.lang.Exception

class TabsActivity : AppCompatActivity(), FirebaseAuth.AuthStateListener {
    private var logined = false

    override fun onAuthStateChanged(auth: FirebaseAuth) {
        Log.d("TabsActivity", "auth state changed ${auth.currentUser}")
        logined = auth.currentUser != null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == 66) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabs)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.selectedItemId = R.id.navigation_timeline

        FirebaseAuth.getInstance().addAuthStateListener(this)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        changeTab(item.itemId)
        true
    }

    private fun changeTab(id: Int): Fragment {
        val fragment = when(id) {
            R.id.navigation_timeline -> TabTimelineFragment.instance()
            R.id.navigation_rank -> TabRankingFragment.instance()
            R.id.navigation_post -> TabUploadFragment.instance()
            R.id.navigation_my_page -> {
                if(logined) TabMyPageFragment.instance()
                else TabLoginFragment.instance()
            }
            else -> throw Exception("Unknown tab id")
        }

        val fm = supportFragmentManager

        if(fm.fragments.isNotEmpty()) {
            fm.beginTransaction()
                    .hide(supportFragmentManager
                            .fragments.first { it.isVisible })
                    .commit()
        }

        if(!fm.fragments.contains(fragment)) {
            fm.beginTransaction()
                    .add(R.id.frame_tab_container, fragment)
                    .commit()
        } else {
            fm.beginTransaction()
                    .show(fragment)
                    .commit()
        }

        return fragment
    }
}
