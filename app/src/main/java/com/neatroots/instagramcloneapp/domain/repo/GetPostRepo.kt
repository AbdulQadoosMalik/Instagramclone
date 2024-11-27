package com.neatroots.instagramcloneapp.domain.repo

import com.neatroots.instagramcloneapp.domain.model.Post

interface GetPostRepo {
    suspend fun getOwnPost(): ArrayList<Post>

    suspend fun getAllPost(): ArrayList<Post>
}