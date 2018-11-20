package io.untaek.animal

import android.app.Application
import android.util.Log
import io.untaek.animal.firebase.Fire

class Initializer: Application() {
    override fun onCreate() {
        super.onCreate()
        Fire.getInstance()
        Fire.Auth.getInstance()
        Log.d("Application", "onCreate")
    }
}