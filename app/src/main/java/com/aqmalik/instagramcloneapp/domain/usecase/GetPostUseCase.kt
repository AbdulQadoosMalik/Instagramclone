package com.aqmalik.instagramcloneapp.domain.usecase

import com.aqmalik.instagramcloneapp.domain.model.Post
import com.aqmalik.instagramcloneapp.domain.repo.GetPostRepo
import javax.inject.Inject

class GetPostUseCase @Inject constructor(private val getPostRepo: GetPostRepo) : GetPostRepo {
    override suspend fun getOwnPost(): ArrayList<Post> =
        getPostRepo.getOwnPost()


    override suspend fun getAllPost(): ArrayList<Post> =
        getPostRepo.getAllPost()


}