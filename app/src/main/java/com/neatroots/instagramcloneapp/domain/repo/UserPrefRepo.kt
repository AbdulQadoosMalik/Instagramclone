package com.neatroots.instagramcloneapp.domain.repo

interface UserPrefRepo {
    suspend fun saveCredentials(email: String, password: String)
    suspend fun loadCredentials(): Pair<String, String>?
}