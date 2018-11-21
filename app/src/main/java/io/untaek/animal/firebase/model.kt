package io.untaek.animal.firebase

import io.opencensus.tags.Tags
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

data class PostInTimeline(
        val id: String,
        val imgResource: Int,
        val userId: String,
        val petId: String,
        val userName: String,
        val petName: String,
        val description: String,
        val likes: Int,
        val postCommentList: List<Comment> = dummy.postComment
): Serializable

data class UserDetail(
        val id: String = "0",
        val userName: String = "0",
        val pictureUrl: String = "0",
        val totalLikes: Long = 0L,
        val totalPosts: Long = 0L,
        val totalFollows: Long = 0L
): Serializable

data class HashTagDetail(
        val tags: String = "0",
        val counts: Long = 0L
): Serializable

data class Comment(
        val commentId: String = "0",
        val userImage: Int = 0,
        val userId: String = "0",
        val userName: String = "0",
        val uploadTime: String = "0",
        val text:String = "0"
): Serializable

data class Content(
        val mime: String = "",
        val h: Int = 0,
        val w: Int = 0,
        val url: String = "",
        var downloadUrl: String = "",
        var thumbUrl: String = ""
): Serializable

data class User(
        val id: String = "0",
        val name: String = "0",
        val pictureUrl: String = "0"
): Serializable

data class Post(
        var id: String = "0",
        val user: User = User(),
        val description: String = "",
        val content: Content = Content(),
        val tags: Map<String, String> = mapOf(),
        var totalLikes: Long = 0,
        val totalComments: Int = 0,
        val comments: ArrayList<Comment2> = arrayListOf(),
        val timeStamp: Date = Date(),
        var like: Boolean = false
): Serializable

data class Comment2(
        var commentId: String = "0",
        var user: User = User(),
        var timeStamp: Date = Date(),
        var commentText: String = "0"
): Serializable

data class Comment_DB(
        val user : User = User("dbsdlswp", "inje", "https://s-i.huffpost.com/gen/4479784/images/n-THRO-628x314.jpg"),
        val timeStamp: Date = Date(),
        val commentText : String
)


data class NewPost(
        val user: User,
        val content: Content,
        val description: String,
        val tags: Map<String, String>,
        val totalLikes: Long,
        val totalComments: Int,
        val timeStamp: Date
)

enum class Type{
    Video,
    Image
}