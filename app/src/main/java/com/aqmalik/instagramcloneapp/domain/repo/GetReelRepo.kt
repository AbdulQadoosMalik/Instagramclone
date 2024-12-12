package com.aqmalik.instagramcloneapp.domain.repo

import com.aqmalik.instagramcloneapp.domain.model.Reel

interface GetReelRepo {
    suspend fun getOwnReel(): ArrayList<Reel>

    suspend fun getAllReel(): ArrayList<Reel>
}