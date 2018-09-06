package io.untaek.animal

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import io.untaek.animal.tab.TabLoginFragment
import io.untaek.animal.tab.TabMyPageFragment
import io.untaek.animal.tab.TabTimelineFragment
import kotlinx.android.synthetic.main.activity_tabs.*

enum class Tab {
    Timeline,
    Rank,
    Post,
    MyPage,
    Login,
}

class TabsActivity : AppCompatActivity() {
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_timeline -> {
                changeTab(Tab.Timeline)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_rank -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_post -> {
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

        FirebaseAuth.getInstance().addAuthStateListener {
            auth -> if (auth.currentUser == null) {
                changeTab(Tab.Timeline)
                Log.d("Auth", "Signed out")
            }else {
                changeTab(Tab.MyPage)
                Log.d("Auth", "Signed in")

            }
        }
        Toast.makeText(this, "${NativeAdapter().helloWorld()}", Toast::LENGTH_SHORT.get()).show()

        Toast.makeText(this, "${NativeAdapter().hello()}", Toast::LENGTH_SHORT.get()).show()
    }

    private fun changeTab(tab: Tab): Fragment {
        val fragment = when(tab) {
            Tab.Timeline -> TabTimelineFragment.instance()
            Tab.MyPage -> TabMyPageFragment.instance()
            Tab.Login -> TabLoginFragment.instance()
            else -> throw IndexOutOfBoundsException("Unexpected fragment index.") }

        fragmentManager
                .beginTransaction()
                .replace(R.id.frame_tab_container, fragment)
                .commitAllowingStateLoss()

        return fragment
    }
}
