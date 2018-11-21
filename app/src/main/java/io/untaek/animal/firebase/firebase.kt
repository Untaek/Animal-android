package io.untaek.animal.firebase

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.google.firebase.auth.FirebaseAuth
import com.firebase.ui.auth.AuthUI
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import io.untaek.animal.R
import io.untaek.animal.tab.RC_SIGN_IN
import io.untaek.animal.util.UploadManager
import java.io.File
import java.lang.Exception
import java.util.*
import kotlin.collections.HashMap

const val POSTS = "posts"
const val USERS = "users"
const val LIKES = "likes"
const val COMMENTS = "comments"
const val FOLLOWS = "follows"
const val TOTAL_LIKES = "totalLikes"
const val TOTAL_POSTS = "totalPosts"
const val TOTAL_FOLLOWS = "totalFollows"
const val TAG = "FireFireFire"
const val TOTAL_COMMENTS = "totalComments"

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

    interface Callback2<T, V> {
        fun onResult(data1: T, data2: V)
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
    fun toggleLike(state: Boolean, postId: String, ownerId: String, callback: Callback<Pair<Boolean, Long>>?) {
        when(state) {
            false -> like(postId, ownerId, callback)
            true -> dislike(postId, ownerId, callback)
        }
    }


    private fun like(postId: String, ownerId: String, callback: Callback<Pair<Boolean, Long>>?) {
        val fs = fs()

        val postRef = fs.collection(POSTS)
                .document(postId)

        val ownerRef = fs.collection(USERS).document(ownerId)

        val userLikesRef = fs.collection(USERS)
                .document(Fire.Auth.getInstance().user().id)
                .collection(LIKES)
                .document(postId)

        FirebaseAuth.getInstance().currentUser?.let {
            fs.runTransaction { t ->
                val newPostTotalLikesValue = t.get(postRef).getLong(TOTAL_LIKES)?.plus(1L)
                val newUserTotalLikesValue = t.get(ownerRef).getLong(TOTAL_LIKES)?.plus(1L)

                t.update(postRef, TOTAL_LIKES, newPostTotalLikesValue)
                t.update(ownerRef, TOTAL_LIKES, newUserTotalLikesValue)
                t.set(userLikesRef, HashMap())

                newPostTotalLikesValue
            }.addOnSuccessListener { after ->
                Log.d("Firestore Transaction", "Success")
                callback?.onResult(Pair(true, after))
            }.addOnFailureListener { e ->
                Log.w("Firestore Transaction", "Failure", e)
                callback?.onFail(e)
            }
        }
    }

    private fun dislike(postId: String, ownerId: String, callback: Callback<Pair<Boolean, Long>>?) {
        val fs = fs()

        val postRef = fs.collection(POSTS)
                .document(postId)

        val ownerRef = fs.collection(USERS).document(ownerId)

        val userLikesRef = fs.collection(USERS)
                .document(Fire.Auth.getInstance().user().id)
                .collection(LIKES)
                .document(postId)

        FirebaseAuth.getInstance().currentUser?.let {
            fs.runTransaction { t ->
                val newPostTotalLikesValue = t.get(postRef).getLong(TOTAL_LIKES)?.minus(1L)
                val newUserTotalLikesValue = t.get(ownerRef).getLong(TOTAL_LIKES)?.minus(1L)

                t.update(postRef, TOTAL_LIKES, newPostTotalLikesValue)
                t.update(ownerRef, TOTAL_LIKES, newUserTotalLikesValue)
                t.delete(userLikesRef)

                newPostTotalLikesValue
            }.addOnSuccessListener { after ->
                Log.d("Firestore Transaction", "Success")
                callback?.onResult(Pair(false, after))
            }.addOnFailureListener { e ->
                Log.w("Firestore Transaction", "Failure", e)
                callback?.onFail(e)
            }
        }
    }

    fun follow(myId: String, userId: String) {
        val fs = fs()
        val myReference = fs.collection(USERS).document(myId)
        val userReference = fs.collection(USERS).document(userId)
        var follow: MutableMap<String, Boolean> = mutableMapOf()
        fs.collection(USERS).document(myId).get().addOnSuccessListener {
            follow = it[FOLLOWS] as MutableMap<String, Boolean>
            follow.put(userId, true)
        }

        FirebaseAuth.getInstance().let {
            fs.runTransaction { t ->
                val targetPost = t.get(myReference)
                val newTotalFollowCount = targetPost.getLong(TOTAL_FOLLOWS)?.plus(1L)
                t.update(myReference, TOTAL_FOLLOWS, newTotalFollowCount)
                t.update(myReference, FOLLOWS, follow)

            }
        }
    }

    fun unFollow() {

    }

    /**
     * Posting
     *
     * Require for entire parameters.
     *
     */


    fun firstreadComments(postId: String, callback: Callback<Pair<DocumentSnapshot?, List<Comment2?>>>) {
        fs().collection(POSTS).document(postId).collection(COMMENTS)
                .orderBy("timeStamp", Query.Direction.DESCENDING)
                .limit(10)
                .get()
                .addOnSuccessListener {
                    callback.onResult(Pair(it.documents.lastOrNull(), it.documents.map {
                        it.toObject(Comment2::class.java).apply {
                            this!!.commentId = it.id
                        }
                    }))
                }
    }

    fun readComments(postId: String, lastSeen: DocumentSnapshot, callback: Callback<Pair<DocumentSnapshot?, List<Comment2?>>>) {
        fs().collection(POSTS).document(postId).collection(COMMENTS)
                .orderBy("timeStamp", Query.Direction.DESCENDING)
                .limit(10)
                .startAfter(lastSeen)
                .get()
                .addOnSuccessListener {
                    callback.onResult(Pair(it.documents.lastOrNull(),
                            it.map {
                                it.toObject(Comment2::class.java).apply {
                                    this.commentId = it.id
                                }
                            }))
                }
    }

    fun newComment(context: Context, postId: String, commentText: String, callback: Callback<Any>) {
        val uploader = User("dbsdlswp", "inje", "https://s-i.huffpost.com/gen/4479784/images/n-THRO-628x314.jpg")
        val comment: Comment_DB = Comment_DB(uploader, Date(), commentText)

        val fs = fs()

        val postRef = fs.collection(POSTS).document(postId)
        val commentRef = fs.collection(POSTS).document(postId).collection(COMMENTS).document()

        FirebaseAuth.getInstance().let {
            fs.runTransaction { t ->
                val targetPost = t.get(postRef)
                val newPostTotalCommentsValue = targetPost.getLong(TOTAL_COMMENTS)?.plus(1L)

                //fs.collection(POSTS).document(postId).collection("comments").add(comment)
                t.update(postRef, TOTAL_COMMENTS, newPostTotalCommentsValue)
                t.set(commentRef, comment)
            }.addOnSuccessListener {
                Log.d("ㅋㅋㅋ", "FireStore Transaction Success")
            }.addOnFailureListener { e ->
                Log.w("ㅋㅋㅋ", "FireStore Transaction Failure", e)
            }
        }
    }


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

        val user = Auth.getInstance().user()

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

                    val userRef = fs().collection(USERS).document(Auth.getInstance().user().id)
                    val postRef = fs().collection(POSTS).document()

                    fs().runTransaction { t ->
                        val newTotalPosts = t.get(userRef).getLong(TOTAL_POSTS)?.plus(1)
                        t.update(userRef, TOTAL_POSTS, newTotalPosts)
                        t.set(postRef, post)
                    }.addOnSuccessListener {
                        Log.d("firestore", "added new Post")
                        callback.onResult(true)
                    }.addOnFailureListener {
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

    fun getFirstPostPage(callback: Callback<Pair<DocumentSnapshot?, List<Post>>>, amount: Long) {
        fs().collection(POSTS)
                .orderBy("timeStamp")
                .limit(amount)
                .get()
                .addOnSuccessListener { qs ->
                    callback.onResult(Pair(qs.documents.lastOrNull(), qs.documents.map {
                        it.toObject(Post::class.java)!!.apply {
                            this.id = it.id
                        }
                    }))
                }
    }

    fun getFirstPostPage(callback: Callback<Pair<DocumentSnapshot?, List<Post>>>){
        getFirstPostPage(callback, 5)
    }

    fun getPostPage(lastSeen: DocumentSnapshot, callback: Callback<Pair<DocumentSnapshot?, List<Post>>>, amount: Long) {
        fs().collection(POSTS)
                .orderBy("timeStamp")
                .limit(amount)
                .startAfter(lastSeen)
                .get()
                .addOnSuccessListener { qs ->
                    callback.onResult(Pair(qs.documents.lastOrNull(), qs.documents.map {
                        it.toObject(Post::class.java)!!.apply {
                            this.id = it.id
                        }
                    }))
                }
    }

    fun getPostPage(lastSeen: DocumentSnapshot, callback: Callback<Pair<DocumentSnapshot?, List<Post>>>) {
        getPostPage(lastSeen, callback, 5)
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

    enum class ThumbSize {
        S64,
        S128,
        M256,
        M512
    }

    private fun resolution(size: ThumbSize): String {
        return when(size) {
            ThumbSize.S64 -> "64"
            ThumbSize.S128 -> "128"
            ThumbSize.M256 -> "256"
            ThumbSize.M512 -> "512"
        }
    }

    fun loadsmallThumbnail(content: Content, context: Context, imageView: ImageView, options: RequestOptions?, callback: Callback<Any>?) {
        loadThumbnail(content, context, imageView, ThumbSize.S64, options, callback)
    }

    fun loadMiddleThumbnail(content: Content, context: Context, imageView: ImageView, options: RequestOptions?, callback: Callback<Any>?) {
        loadThumbnail(content, context, imageView, ThumbSize.M256, options, callback)
    }

    fun loadThumbnail(content: Content, context: Context, imageView: ImageView, size: ThumbSize, options: RequestOptions?, callback: Callback<Any>?) {
        val thumbUrl =
                if (content.mime.startsWith("image/"))
                    "thumb@${resolution(size)}_${content.url}"
        else "thumb@${resolution(size)}_${content.url.split(".")[0]}.jpg"
        storage().reference.child(thumbUrl).downloadUrl
                .addOnSuccessListener { uri ->
                    val builder = Glide.with(context)
                            .asBitmap()
                            .load(uri)

                    if(options != null) {
                        builder.apply(options).into(imageView)
                    }else {
                        builder.into(imageView)
                    }

                }
                .addOnFailureListener {
                    Log.w(TAG, "thumbnail error", it)
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
    }
}