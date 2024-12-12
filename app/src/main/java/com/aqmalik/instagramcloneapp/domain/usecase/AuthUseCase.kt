package com.aqmalik.instagramcloneapp.domain.usecase

import com.aqmalik.instagramcloneapp.domain.model.UserData
import com.aqmalik.instagramcloneapp.domain.repo.AuthRepo
import javax.inject.Inject


class AuthUseCase @Inject constructor(
    private val authRepo: AuthRepo
) : AuthRepo {

    // SignUp Use Case
    override suspend fun signUpWithEmailAndPassword(
        userData: UserData
    ): Result<Unit>? = userData.profileImageUrl?.let {
        authRepo.signUpWithEmailAndPassword(userData = userData)
    }

    // Login Use Case
    override suspend fun signInWithEmailAndPassword(userData: UserData): Result<Unit> =
        authRepo.signInWithEmailAndPassword(userData = userData)


    // Update User Use Case
    override suspend fun updateUser(userData: UserData): Result<Unit> =
        authRepo.updateUser(userData = userData)

    // Get User Details
    override suspend fun getUserDetails(): Result<UserData?> =
        authRepo.getUserDetails()

}