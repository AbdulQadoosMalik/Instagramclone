package com.neatroots.instagramcloneapp.domain.usecase

import com.neatroots.instagramcloneapp.domain.model.Reel
import com.neatroots.instagramcloneapp.domain.repo.CreateReelRepo
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