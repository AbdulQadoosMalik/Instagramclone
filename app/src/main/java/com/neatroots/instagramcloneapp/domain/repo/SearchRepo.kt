package com.neatroots.instagramcloneapp.domain.repo

import com.neatroots.instagramcloneapp.domain.model.UserData

interface SearchRepo {
    suspend fun search(name: String) : ArrayList<UserData>
}