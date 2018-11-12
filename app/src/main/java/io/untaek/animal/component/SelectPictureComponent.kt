package io.untaek.animal.component

import android.content.Context
import android.content.Intent
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import io.untaek.animal.R
import io.untaek.animal.TimelineDetailActivity
import io.untaek.animal.UploadActivity

class SelectPictureComponent: ConstraintLayout, View.OnClickListener {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    init {
        val li: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = li.inflate(R.layout.component_select_picture, this, false)
        view.setOnClickListener(this)
        addView(view)
    }

    override fun onClick(v: View) {
        context.startActivity(Intent(context, UploadActivity::class.java))
        Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show()
    }
}