package io.untaek.animal.firebase

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import com.firebase.ui.auth.AuthUI
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*


class Fire(private val context: Context) {

    fun toggleLike(postId: String) {
        FirebaseFirestore.getInstance()
                .runTransaction {
                    
                }
    }

    fun toggleFollow() {

    }

    class Auth(private val context: Context) {
        fun connectionChecker() {

        }

        fun sendEmailLink(email: String) {
            val actionCodeSettings = ActionCodeSettings.newBuilder()
                    .setUrl("https://animalauth.page.link")
                    .setHandleCodeInApp(true)
                    .setAndroidPackageName(
                            "com.untaekPost.animal",
                            true,
                            "19"
                    )
                    .build()

            val auth = FirebaseAuth.getInstance()
            auth.sendSignInLinkToEmail(email, actionCodeSettings)
                    .addOnCompleteListener {
                        _ -> Log.d("sendSignInLinkToEmail", "Email sent.")
                        context.getSharedPreferences("temp", 0)
                                .edit().putString("email", email).apply()
                    }
        }

        fun completeSignInWithEmailLink(emailLink: String) {
            val auth = FirebaseAuth.getInstance()

            if (auth.isSignInWithEmailLink(emailLink)) {
                val email = context.getSharedPreferences("temp", 0).getString("email", null)
                auth.signInWithEmailLink(email, emailLink)
                        .addOnCompleteListener {
                            task ->
                            if(task.isSuccessful) {
                                Log.d(TAG, "Successfully signed in with email link!")
                                val result = task.result

                            }
                        }
            }
        }

        companion object {
             fun getDefaultProviders(): List<AuthUI.IdpConfig> {
                 return Arrays.asList(
                         AuthUI.IdpConfig.GoogleBuilder().build(),
                         AuthUI.IdpConfig.FacebookBuilder().build())
             }
        }

        fun signOut() {
            AuthUI.getInstance()
                    .signOut(context)
                    .addOnCompleteListener {
                        Log.d(TAG, "Successfully signed out.")
                    }
        }
    }

    class Storage(private val context: Context) {
        fun uploadVideo() {
//          FirebaseStorage.getInstance().reference.child("original/").putStream()
        }

        fun uploadThumbnail() {

        }

        fun uploadPhoto() {

        }

        fun downloadVideo() {
            FirebaseStorage.getInstance().reference.child("/")
        }

        fun downloadImage() {

        }
    }
}