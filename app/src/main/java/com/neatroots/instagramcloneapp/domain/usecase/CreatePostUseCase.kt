package com.neatroots.instagramcloneapp.domain.usecase

import com.neatroots.instagramcloneapp.domain.model.Post
import com.neatroots.instagramcloneapp.domain.repo.CreatePostRepo
import javax.inject.Inject

class CreatePostUseCase @Inject constructor(private val createPostRepo: CreatePostRepo) : CreatePostRepo {
    override suspend fun createPost(
        post: Post, onSuccess: () -> Unit, onFailure: (Exception) -> Unit
    ) = createPostRepo.createPost(post, onSuccess, onFailure)
}