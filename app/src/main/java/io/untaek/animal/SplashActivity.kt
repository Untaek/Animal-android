package io.untaek.animal

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activity =
                if (FirebaseAuth.getInstance().currentUser == null)
                    LoginActivity::class.java
                else
                    TabsActivity::class.java

        val intent = Intent(this, activity)
        startActivity(intent)
        finish()
    }
}
