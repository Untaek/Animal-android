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
        val id: String,
        val userName: String,
        val imgResource: Int,
        val totalLikes: Long,
        val posts: Long,
        val Follows: Long
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
        val type: Type = Type.Image,
        val h: Int = 0,
        val w: Int = 0,
        val url: String = ""
): Serializable

data class User(
        val id: String = "0",
        val name: String = "0",
        val picture_url: String = "0"
): Serializable

data class Post(
        val id: String = "0",
        val user: User = User(),
        val description: String = "",
        val content: Content = Content(),
        val tags: Map<String, String> = mapOf(),
        val totalLikes: Long = 0,
        val totalComments: Int = 0,
        val comments: ArrayList<Comment2> = arrayListOf(),
        val timeStamp: Date = Date()
): Serializable

data class Comment2(
        val commentId: String = "0",
        val user: User = User(),
        val timeStamp: Date = Date(),
        val text: String = "0"
): Serializable

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