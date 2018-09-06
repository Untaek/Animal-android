package io.untaek.animal.tab

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import io.untaek.animal.R
import io.untaek.animal.firebase.Fire
import kotlinx.android.synthetic.main.tab_my_page.*

class TabLoginFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val root = inflater!!.inflate(R.layout.tab_login, container, false)

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Fire.Auth.getDefaultProviders())
                        .setIsSmartLockEnabled(false)
                        .setLogo(R.drawable.ic_launcher_background)
                        .build(), RC_SIGN_IN)
        return root
    }

    companion object {
        fun instance(): TabLoginFragment {
            return TabLoginFragment()
        }
    }
}