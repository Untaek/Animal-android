package io.untaek.animal.firebase

import java.io.Serializable

data class PostInTimeline(
        val id: String,
        val userId: String,
        val petId: String,
        val userName: String,
        val petName: String,
        val description: String,
        val likes: Int): Serializable

data class UserDetail(
        val id: String,
        val userName: String,
        val totalLikes: Long,
        val posts: Long,
        val Follows: Long
): Serializable