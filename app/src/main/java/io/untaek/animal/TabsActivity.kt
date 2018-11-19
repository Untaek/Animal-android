package io.untaek.animal

import android.content.Intent
import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import io.untaek.animal.firebase.Fire
import io.untaek.animal.tab.*
import kotlinx.android.synthetic.main.activity_tabs.*

enum class Tab {
    Timeline,
    Rank,
    Upload,
    MyPage,
    Login,
}

class TabsActivity : AppCompatActivity() {

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d("onActivityResult", "Attempt login")
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        val account = task.getResult(ApiException::class.java)
        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
        val auth = FirebaseAuth.getInstance()

        auth.signInWithCredential(credential)
                .addOnSuccessListener {
                    Log.d("onActivityResult", auth.currentUser.toString())
                    if(it.additionalUserInfo.isNewUser) {
                        Toast.makeText(this, "가입을 환영합니다!", Toast.LENGTH_SHORT).show()
                        Fire.Auth.getInstance().createUser(it.user, null)
                        navigation.selectedItemId = R.id.navigation_my_page
                    }
                }
                .addOnFailureListener {
                    Log.d("onActivityResult", it.message)
                }
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_timeline -> {
                changeTab(Tab.Timeline)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_rank -> {
                changeTab(Tab.Rank)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_post -> {
                changeTab(Tab.Upload)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_my_page -> {
                if(FirebaseAuth.getInstance().currentUser == null) {
                    changeTab(Tab.Login)
                }else{
                    changeTab(Tab.MyPage)
                }

                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabs)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.selectedItemId = R.id.navigation_timeline
    }

    private fun changeTab(tab: Tab): Fragment {
        val fragment = when(tab) {
            Tab.Timeline -> TabTimelineFragment.instance()
            Tab.Rank -> TabRankingFragment.instance()
            Tab.MyPage -> TabMyPageFragment.instance()
            Tab.Upload -> TabUploadFragment.instance()
            Tab.Login -> TabLoginFragment.instance()
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
