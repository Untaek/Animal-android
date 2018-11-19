package io.untaek.animal.component

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import io.untaek.animal.R
import io.untaek.animal.UploadActivity
import io.untaek.animal.firebase.Fire
import kotlinx.android.synthetic.main.component_select_picture.view.*

class SelectPictureComponent: ConstraintLayout, View.OnClickListener {
    lateinit var icon: Drawable
    lateinit var text: String
    var target: Int = IMAGE

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.SelectPictureComponent,
                0, 0
        ).apply {
            try{
                icon = getDrawable(R.styleable.SelectPictureComponent_icon)
                text = getString(R.styleable.SelectPictureComponent_text)
                target = getInt(R.styleable.SelectPictureComponent_target, IMAGE)
            } finally {
                recycle()
            }
        }

        val li: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = li.inflate(R.layout.component_select_picture, this, false)
        view.setOnClickListener(this)
        view.select_picture_symbol.background = icon
        view.textView_text_type.text = text
        addView(view)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onClick(v: View) {
        if(Fire.Auth.getInstance().user() == null) {
            Toast.makeText(context, "로그인이 필요합니다", Toast.LENGTH_SHORT).show()
            return
        }

        context.startActivity(
                Intent(context, UploadActivity::class.java).apply { putExtra(TARGET, target) }
        )
    }

    companion object {
        const val TARGET = "TARGET"
        const val IMAGE = 0
        const val VIDEO = 1
        const val CAMERA = 2
    }
}