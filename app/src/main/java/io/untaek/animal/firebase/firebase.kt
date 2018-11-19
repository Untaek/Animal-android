package io.untaek.animal.firebase

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.firebase.auth.FirebaseAuth
import com.firebase.ui.auth.AuthUI
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import com.google.firebase.storage.FirebaseStorage
import io.untaek.animal.util.UploadManager
import java.io.File
import java.lang.Exception
import java.util.*
import kotlin.collections.HashMap

const val POSTS = "posts"
const val USERS = "users"
const val LIKES = "likes"
const val TOTAL_LIKES = "totalLikes"
const val TOTAL_POSTS = "totalPosts"
const val TOTAL_FOLLOWS = "totalFollows"
const val TAG = "FireFireFire"

class Fire {

    companion object {
        /**
         * Makes singleton of instance
         */
        fun getInstance() = Fire()

        /**
         * Instance of Firebase features
         */
        private fun fs() = FirebaseFirestore.getInstance()
        private fun storage() = FirebaseStorage.getInstance()
        private fun auth() = FirebaseAuth.getInstance()
    }

    /**
     * For receiving result of method
     */
    interface Callback<T> {
        fun onResult(data: T)
        fun onFail(e: Exception)
    }

    interface ProgressCallback {
        fun onProgress(percentage: Long)
    }

    private val firestoreSettings = FirebaseFirestoreSettings.Builder()
            .setTimestampsInSnapshotsEnabled(true)
            .build()

    /**
     * Toggling like, follow
     *
     * fun like(postId: String)
     * fun dislike(postId: String)
     *
     * fun follow(postId: String)
     * fun unFollow(postId: String)
     */

    fun like(postId: String) {
        val fs = fs()

        val postRef = fs.collection(POSTS)
                .document(postId)

        val userLikesRef = fs.collection(USERS)
                .document(Fire.Auth.getInstance().user().id)
                .collection(LIKES)
                .document(postId)

        FirebaseAuth.getInstance().currentUser?.let {
            fs.runTransaction { t ->
                val targetPost = t.get(postRef)
                val ownerUser = t.get(fs.collection(USERS).document(((targetPost.get("user") as Map<*, *>)["id"] as String?)!!))
                val postOwnerRef = fs.collection(USERS).document(ownerUser.id)
                val newPostTotalLikesValue = targetPost.getLong(TOTAL_LIKES)?.plus(1L)
                val newUserTotalLikesValue = t.get(postOwnerRef).getLong(TOTAL_LIKES)?.plus(1L)

                t.update(postRef, TOTAL_LIKES, newPostTotalLikesValue)
                t.update(postOwnerRef, TOTAL_LIKES, newUserTotalLikesValue)
                t.set(userLikesRef, true)

                null
            }.addOnSuccessListener { _ ->
                Log.d("Firestore Transaction", "Success")
            }.addOnFailureListener { e ->
                Log.w("Firestore Transaction", "Failure", e)
            }
        }
    }

    fun dislike(postId: String) {
        val fs = fs()

        val postRef = fs.collection(POSTS)
                .document(postId)

        val userLikesRef = fs.collection(USERS)
                .document(Fire.Auth.getInstance().user().id)
                .collection(LIKES)
                .document(postId)

        FirebaseAuth.getInstance().currentUser?.let {
            fs.runTransaction { t ->
                val targetPost = t.get(postRef)
                val ownerUser = t.get(fs.collection(USERS).document(((targetPost.get("user") as Map<*, *>)["id"] as String?)!!))
                val postOwnerRef = fs.collection(USERS).document(ownerUser.id)
                val newPostTotalLikesValue = targetPost.getLong(TOTAL_LIKES)?.minus(1L)
                val newUserTotalLikesValue = t.get(postOwnerRef).getLong(TOTAL_LIKES)?.minus(1L)

                t.update(postRef, TOTAL_LIKES, newPostTotalLikesValue)
                t.update(postOwnerRef, TOTAL_LIKES, newUserTotalLikesValue)
                t.delete(userLikesRef)

                null
            }.addOnSuccessListener { _ ->
                Log.d("Firestore Transaction", "Success")
            }.addOnFailureListener { e ->
                Log.w("Firestore Transaction", "Failure", e)
            }
        }
    }

    fun follow() {

    }

    fun unFollow() {

    }

    /**
     * Posting
     *
     * Require for entire parameters.
     *
     */

    fun newPost(context: Context, tags: Map<String, String>, description: String, contentUri: Uri, callback: Callback<Any>, progressCallback: ProgressCallback?) {
        if(Fire.Auth.getInstance().firebaseUser() == null) {
            callback.onFail(Exception("Unauthenticated"))
            return
        }

        val uid = Fire.Auth.getInstance().firebaseUser()?.uid!!
        val resolution = UploadManager.getSize(context, contentUri)
        val mime = UploadManager.getMime(context, contentUri)
        val url = "${uid}_${Date().time}.${if (mime.startsWith("image")) "jpg" else "mp4"}"

        val content = Content(mime, resolution.first, resolution.second, url)

        fs().collection(USERS).document(uid).get().addOnSuccessListener {
            val user = User(it.id, it.getString("name")?: "Unknown", it.getString("pictureUrl")?: "")
            //context.grantUriPermission()
            storage().reference.child(content.url).putStream(context.contentResolver.openInputStream(contentUri))
                    .addOnSuccessListener { _ ->
                        val post = NewPost(
                                user,
                                content,
                                description,
                                tags,
                                0L,
                                0,
                                Date()
                        )

                        fs().collection(POSTS).add(post)
                                .addOnSuccessListener {
                                    Log.d("firestore", "added ${it.id}")
                                    callback.onResult(it.id)
                                }
                                .addOnFailureListener {
                                    Log.w("firestore", "error occurred", it)
                                    callback.onFail(it)
                                }
                    }
                    .addOnProgressListener {
                        progressCallback?.onProgress(it.bytesTransferred / it.totalByteCount)
                    }
                    .addOnFailureListener {
                        callback.onFail(it)
                    }
        }
    }

    fun getFirstPostPage(callback: Callback<Pair<DocumentSnapshot?, List<Post>>>){
        fs().collection(POSTS)
                .orderBy("timeStamp")
                .limit(5)
                .get()
                .addOnSuccessListener { qs ->
                    callback.onResult(Pair(qs.documents.lastOrNull(), qs.documents.map { it.toObject(Post::class.java)!! }))
                }
    }

    fun getPostPage(lastSeen: DocumentSnapshot, callback: Callback<Pair<DocumentSnapshot?, List<Post>>>) {
        fs().collection(POSTS)
                .orderBy("timeStamp")
                .limit(5)
                .startAt(lastSeen)
                .get()
                .addOnSuccessListener { qs ->
                    callback.onResult(Pair(qs.documents.lastOrNull(), qs.documents.map { it.toObject(Post::class.java)!!}))
                }
    }

    fun postDetail(postId: String, callback: Callback<Post>) {
        fs().collection(POSTS).document(postId).get()
                .addOnSuccessListener { snap ->
                    Log.d("Post", "${snap.id} ${snap.data?.size}")
                    val post = snap.toObject(Post::class.java)
                    post?.let {
                        callback.onResult(post)
                    }
                }
                .addOnFailureListener {
                    Log.w("firestore", "error occurred", it)
                    callback.onFail(it)
                }
    }

    fun loadThumbnail(content: Content, context: Context, imageView: ImageView, callback: Callback<Any>?) {
        storage().reference.child("thumb@256_${content.url}").downloadUrl
                .addOnSuccessListener { uri ->
                    Glide.with(context)
                            .asBitmap()
                            .load(uri)
                            .into(imageView)
                }
    }

    fun loadActualContent(content: Content, context: Context, callback: Callback<Any>) {
        Log.d("LoadContent", "Try to load $content")

        val type = content.mime.split("/")[0]

        if(type == "image") {
            storage().reference.child(content.url).downloadUrl
                    .addOnSuccessListener { uri ->
                        Log.d(TAG, "uri $uri")
                        Glide.with(context)
                                .asBitmap()
                                .load(uri)
                                .listener(object : RequestListener<Bitmap> {
                                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
                                        callback.onFail(e!!)
                                        return true
                                    }

                                    override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                                        callback.onResult(resource!!)
                                        return true
                                    }
                                })
                                .submit(content.w, content.h)
                    }
                    .addOnFailureListener {
                        callback.onFail(it)
                    }
        }
        else if (type == "video") {
            val file = File(context.cacheDir, content.url)

            Log.d("Video file", file.absolutePath)

            if(file.exists()) {
                Log.d("Video file", "exist")
                callback.onResult(file)
            }
            else {
                file.createNewFile()
                storage().reference.child(content.url)
                        .getFile(file)
                        .addOnProgressListener {  }
                        .addOnSuccessListener {
                            callback.onResult(file)
                        }
                        .addOnFailureListener{
                            callback.onFail(it)
                        }
            }
        }
    }

    /**
     * User Authentication class
     */
    class Auth private constructor(): FirebaseAuth.AuthStateListener {

        /**
         * Listener of authentication state
         *
         * UserId will be If user is logging in,
         * If not will be "0"
         */
        override fun onAuthStateChanged(auth: FirebaseAuth) {
            Log.d(TAG, "auth state changed!")
            user = auth.currentUser?.let {
                val uid = it.uid
                val name = it.displayName!!
                val pictureUrl = it.photoUrl!!

                User(uid, name, pictureUrl.toString())
            } ?: User()

            Log.d(TAG, "$user")
        }

        init {
            FirebaseAuth.getInstance().addAuthStateListener(this)
        }

        fun signOut(context: Context) {
            FirebaseAuth.getInstance()
                    .signOut()
        }

        fun createUser(user: FirebaseUser, callback: Callback<Any>?) {
            Log.d(TAG, "${user.photoUrl}")
            val map: HashMap<String, Any> = HashMap<String, Any>().apply {
                put("name", user.displayName!!)
                put("pictureUrl", user.photoUrl.toString())
                put("totalLikes", 0)
                put("totalPosts", 0)
                put("totalFollowers", 0)
                put("likes", HashMap<String, Any>())
                put("comments", HashMap<String, Any>())
                put("followers", HashMap<String, Any>())
                put("timeStamp", Timestamp(Date()))
            }

            FirebaseFirestore.getInstance()
                    .collection(USERS)
                    .document(user.uid)
                    .set(map)
                    .addOnSuccessListener {
                        callback?.onResult(it)
                    }
                    .addOnFailureListener {
                        callback?.onFail(it)
                    }
        }

        fun firebaseUser(): FirebaseUser? {
            return FirebaseAuth.getInstance().currentUser
        }

        fun user() = user

        companion object {
            private var instance: Auth? = null
            private var user: User = User()
            fun getInstance(): Auth = if(instance == null) Auth() else instance!!
        }


        //        fun connectionChecker() {
//
//        }
//
//        fun sendEmailLink(email: String) {
//            val actionCodeSettings = ActionCodeSettings.newBuilder()
//                    .setUrl("https://animalauth.page.link")
//                    .setHandleCodeInApp(true)
//                    .setAndroidPackageName(
//                            "com.untaekPost.animal",
//                            true,
//                            "21"
//                    )
//                    .build()
//
//            val auth = FirebaseAuth.getInstance()
//            auth.sendSignInLinkToEmail(email, actionCodeSettings)
//                    .addOnCompleteListener {
//                        _ -> Log.d("sendSignInLinkToEmail", "Email sent.")
//                        context.getSharedPreferences("temp", 0)
//                                .edit().putString("email", email).apply()
//                    }
//        }
//
//        fun completeSignInWithEmailLink(emailLink: String) {
//            val auth = FirebaseAuth.getInstance()
//
//            if (auth.isSignInWithEmailLink(emailLink)) {
//                val email = context.getSharedPreferences("temp", 0).getString("email", null)
//                auth.signInWithEmailLink(email, emailLink)
//                        .addOnCompleteListener {
//                            task ->
//                            if(task.isSuccessful) {
//                                Log.d(TAG, "Successfully signed in with email link!")
//                                val result = task.result
//
//                            }
//                        }
//            }
//        }
    }
}