package io.untaek.animal

import android.app.Fragment
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import io.untaek.animal.tab.TabTimelineFragment
import kotlinx.android.synthetic.main.activity_tabs.*

class TabsActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                changeTab(0)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_my_page -> {
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabs)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.selectedItemId = R.id.navigation_home
    }

    private fun changeTab(num: Int): Fragment {
        val fragment = when(num) {
            0 -> TabTimelineFragment.instance()
            else -> throw IndexOutOfBoundsException("Unexpected fragment index.") }

        fragmentManager
                .beginTransaction()
                .replace(R.id.frame_tab_container, fragment)
                .commit()

        return fragment
    }
}