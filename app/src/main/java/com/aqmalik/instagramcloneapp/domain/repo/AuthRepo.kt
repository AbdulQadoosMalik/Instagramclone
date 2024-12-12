package com.aqmalik.instagramcloneapp.domain.repo

import com.aqmalik.instagramcloneapp.domain.model.UserData

interface AuthRepo {
    suspend fun signUpWithEmailAndPassword(userData: UserData): Result<Unit>?
    suspend fun signInWithEmailAndPassword(userData: UserData): Result<Unit>
    suspend fun updateUser(userData: UserData): Result<Unit>
    suspend fun getUserDetails(): Result<UserData?>
}

