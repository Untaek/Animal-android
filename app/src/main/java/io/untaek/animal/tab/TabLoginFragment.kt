package io.untaek.animal.tab

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v4.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import io.untaek.animal.R

class TabLoginFragment: Fragment() {

    val TAG = "TabLoginFragment"

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (requestCode == RC_SIGN_IN) {
//            Log.d(TAG, "google login onActivityResult")
//
//            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//            val account = task.getResult(ApiException::class.java)
//            val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
//            val auth = FirebaseAuth.getInstance()
//            auth.signInWithCredential(credential)
//                    .addOnSuccessListener {
//                        Log.d(TAG, auth.currentUser.toString())
//                    }
//                    .addOnFailureListener {
//                        Log.d(TAG, it.message)
//                    }
//        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.tab_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var THIS: TabLoginFragment? = null
        fun instance(): TabLoginFragment {
            if(THIS == null) {
                THIS = TabLoginFragment()
            }
            return THIS!!
        }
    }
}