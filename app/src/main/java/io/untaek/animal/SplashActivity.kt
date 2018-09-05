package io.untaek.animal

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

const val MIN_DELAY = 400L

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(this, TabsActivity::class.java)
        Thread.sleep(MIN_DELAY)
        startActivity(intent)
        finish()
    }
}
