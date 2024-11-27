package com.neatroots.instagramcloneapp.domain.usecase

import com.neatroots.instagramcloneapp.domain.repo.UserPrefRepo
import javax.inject.Inject

class UserPrefUseCase @Inject constructor(private val userPrefRepo: UserPrefRepo) : UserPrefRepo {
    override suspend fun saveCredentials(email: String, password: String) {
        userPrefRepo.saveCredentials(email, password)
    }

    override suspend fun loadCredentials(): Pair<String, String>? {
        return userPrefRepo.loadCredentials()
    }
}