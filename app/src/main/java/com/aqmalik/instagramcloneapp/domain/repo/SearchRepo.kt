package com.aqmalik.instagramcloneapp.domain.repo

import com.aqmalik.instagramcloneapp.domain.model.UserData

interface SearchRepo {
    suspend fun search(name: String) : ArrayList<UserData>
}