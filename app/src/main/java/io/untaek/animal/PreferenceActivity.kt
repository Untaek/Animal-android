package io.untaek.animal

import android.app.Activity
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import io.untaek.animal.TabsActivity.Companion.LOG_OUT
import io.untaek.animal.firebase.Content
import io.untaek.animal.firebase.Fire
import kotlinx.android.synthetic.main.activity_preference.*
import kotlinx.android.synthetic.main.item_preference.view.*

class PreferenceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preference)

        recyclerView_preference.adapter = PreferenceAdapter(this)
        recyclerView_preference.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView_preference.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }
}

class PreferenceAdapter(val context: Context): RecyclerView.Adapter<PreferenceAdapter.ViewHolder>(), FirebaseAuth.AuthStateListener {
    val TAG = "PreferenceAdapter"

    data class Menu(val label: String, val type: Int)

    private val items: ArrayList<Menu> = arrayListOf()

    init {
        items.add(Menu("공지사항", 0))
        items.add(Menu("로그아웃", 1))

        FirebaseAuth.getInstance().addAuthStateListener(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_preference, parent, false), items)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.textView_label.text = item.label
    }

    override fun onAuthStateChanged(p0: FirebaseAuth) {
        Log.d(TAG, "onAuthStateChanged, ${p0.currentUser}")
        if(p0.currentUser == null){
            (context as Activity).setResult(LOG_OUT)
            (context as Activity).finish()
        }
    }

    class ViewHolder(itemView: View, items: ArrayList<Menu>): RecyclerView.ViewHolder(itemView) {
        private val context: Context = itemView.context

        val textView_label: TextView = itemView.textView_label

        init {
            itemView.setOnClickListener {
                when(items[adapterPosition].type){
                    0 -> {

                    }

                    1 -> {
                        AlertDialog.Builder(context)
                            .setMessage("정말로 로그아웃 하시겠습니까?")
                            .setPositiveButton("네") { dialog, _ ->
                                Fire.Auth.getInstance().signOut(context)
                                dialog.dismiss()
                            }
                            .setNegativeButton("아니오") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .create()
                            .show()
                    }
                }
            }
        }
    }
}
