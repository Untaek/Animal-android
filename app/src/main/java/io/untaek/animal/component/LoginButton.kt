package io.untaek.animal.component

import android.app.Activity
import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import io.untaek.animal.R
import io.untaek.animal.firebase.Fire
import io.untaek.animal.tab.RC_SIGN_IN
import kotlinx.android.synthetic.main.component_button_login.view.*
import java.lang.Exception

class LoginButton: ConstraintLayout, View.OnClickListener {
    var type: Int = -1

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.LoginButton,
                0, 0
        ).apply {
            try{
                type = getInt(R.styleable.LoginButton_type, -1)
            } finally {
                recycle()
            }
        }
        val li: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = li.inflate(R.layout.component_button_login, this, false)
        view.textView_text.text = selectProviderText(type)
        view.setOnClickListener(this)
        addView(view)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onClick(v: View?) {
        when(type) {
            GOOGLE -> googleLogin(context)
        }
    }

    private fun selectProviderText(type: Int): String {
        return when(type) {
            GOOGLE -> "Login with Google"
            FACEBOOK -> "Login with Facebook"
            TWITTER -> "Login with Twitter"
            else -> throw Exception("Wrong type")
        }
    }

    private fun googleLogin(context: Context) {
        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.google_client_id))
                .requestProfile()
                .requestEmail()
                .build()

        val client = GoogleSignIn.getClient(context, options)
        (context as Activity).startActivityForResult(client.signInIntent, RC_SIGN_IN)
    }


    companion object {
        const val GOOGLE = 0
        const val FACEBOOK = 1
        const val TWITTER = 2
    }
}
