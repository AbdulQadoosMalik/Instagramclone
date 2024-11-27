package com.neatroots.instagramcloneapp.domain.repo

import com.neatroots.instagramcloneapp.domain.model.Post


interface CreatePostRepo {
    suspend fun createPost(post: Post, onSuccess: () -> Unit, onFailure: (Exception) -> Unit)
}
