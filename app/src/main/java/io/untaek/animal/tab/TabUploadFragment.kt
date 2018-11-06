package io.untaek.animal.tab

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.untaek.animal.R

class TabUploadFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.tab_upload, container, false)

        return v
    }

    companion object {
        fun instance(): TabUploadFragment {
            return TabUploadFragment()
        }
    }
}