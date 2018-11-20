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
import io.untaek.animal.LoginActivity
import io.untaek.animal.R
import kotlinx.android.synthetic.main.tab_login.view.*

class TabLoginFragment: Fragment() {

    val TAG = "TabLoginFragment"



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.tab_login, container, false)
    }

    override fun onViewCreated(root: View, savedInstanceState: Bundle?) {
        root.button_login.setOnClickListener {
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }
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