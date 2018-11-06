package io.untaek.animal.firebase

data class PostInTimeline(
        val id: String,
        val userId: String,
        val petId: String,
        val title: String,
        val userName: String,
        val petName: String,
        val description: String,
        val likes: Int)