package com.aqmalik.instagramcloneapp.domain.repo

import com.aqmalik.instagramcloneapp.domain.model.Post

interface GetPostRepo {
    suspend fun getOwnPost(): ArrayList<Post>

    suspend fun getAllPost(): ArrayList<Post>
}