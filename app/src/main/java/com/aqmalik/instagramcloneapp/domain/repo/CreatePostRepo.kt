package com.aqmalik.instagramcloneapp.domain.repo

import com.aqmalik.instagramcloneapp.domain.model.Post


interface CreatePostRepo {
    suspend fun createPost(post: Post, onSuccess: () -> Unit, onFailure: (Exception) -> Unit)
}
