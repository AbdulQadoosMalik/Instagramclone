package com.neatroots.instagramcloneapp.domain.usecase

import com.neatroots.instagramcloneapp.domain.model.Post
import com.neatroots.instagramcloneapp.domain.repo.GetPostRepo
import javax.inject.Inject

class GetPostUseCase @Inject constructor(private val getPostRepo: GetPostRepo) : GetPostRepo {
    override suspend fun getOwnPost(): ArrayList<Post> =
        getPostRepo.getOwnPost()


    override suspend fun getAllPost(): ArrayList<Post> =
        getPostRepo.getAllPost()


}