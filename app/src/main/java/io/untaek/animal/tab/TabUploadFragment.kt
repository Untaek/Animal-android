package io.untaek.animal.tab

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.untaek.animal.R

class TabUploadFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.tab_upload, container, false)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var THIS: TabUploadFragment? = null
        fun instance(): TabUploadFragment {
            if(THIS == null) {
                THIS = TabUploadFragment()
            }
            return THIS!!
        }
    }
}