package com.aqmalik.instagramcloneapp.domain.usecase

import com.aqmalik.instagramcloneapp.domain.model.Post
import com.aqmalik.instagramcloneapp.domain.repo.CreatePostRepo
import javax.inject.Inject

class CreatePostUseCase @Inject constructor(private val createPostRepo: CreatePostRepo) : CreatePostRepo {
    override suspend fun createPost(
        post: Post, onSuccess: () -> Unit, onFailure: (Exception) -> Unit
    ) = createPostRepo.createPost(post, onSuccess, onFailure)
}