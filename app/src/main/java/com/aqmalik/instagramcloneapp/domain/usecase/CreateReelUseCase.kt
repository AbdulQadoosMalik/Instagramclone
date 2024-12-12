package com.aqmalik.instagramcloneapp.domain.usecase

import com.aqmalik.instagramcloneapp.domain.model.Reel
import com.aqmalik.instagramcloneapp.domain.repo.CreateReelRepo
import javax.inject.Inject

class CreateReelUseCase @Inject constructor(private val createReelRepo: CreateReelRepo) :
    CreateReelRepo {
    override suspend fun createReel(
        reel: Reel,
        onProgress: (Int) -> Unit,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        createReelRepo.createReel(reel, onProgress, onSuccess, onFailure)
    }

}