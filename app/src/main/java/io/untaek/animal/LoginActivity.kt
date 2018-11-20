package io.untaek.animal

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import io.untaek.animal.firebase.Fire
import io.untaek.animal.tab.RC_SIGN_IN
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_tabs.*

class LoginActivity : AppCompatActivity() {

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("onActivityResult", "request code $requestCode")
        if(requestCode == RC_SIGN_IN) {
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
                        startActivity(Intent(this, TabsActivity::class.java))
                        finish()
                    }
                    .addOnFailureListener {
                        Log.d("onActivityResult", it.message)
                    }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        textView_without_login.setOnClickListener {
            startActivity(Intent(this, TabsActivity::class.java))
            finish()
        }
    }
}
