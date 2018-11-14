package io.untaek.animal.firebase

import java.io.Serializable

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
        val commentId: String,
        val userImage: Int,
        val userId: String,
        val userName: String,
        val uploadTime: String,
        val text:String
): Serializable

data class UserDetail_(
        val id: String,
        val userName : String,
        val photoURL : String,
        val total_posts: Int,
        val total_likes: Int,
        val total_followers: Int,
        val posts : List<String>,
        val follow : List<String>,
        val like : List<String>
):Serializable

