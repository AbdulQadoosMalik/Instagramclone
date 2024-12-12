package com.aqmalik.instagramcloneapp.domain.usecase

import com.aqmalik.instagramcloneapp.domain.model.Reel
import com.aqmalik.instagramcloneapp.domain.repo.GetReelRepo
import javax.inject.Inject

class GetReelUseCase @Inject constructor(private val getReelRepo: GetReelRepo) : GetReelRepo {
    override suspend fun getOwnReel(): ArrayList<Reel> =
        getReelRepo.getOwnReel()


    override suspend fun getAllReel(): ArrayList<Reel> =
        getReelRepo.getAllReel()



}