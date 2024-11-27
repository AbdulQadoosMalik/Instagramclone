package com.neatroots.instagramcloneapp.domain.repo

import com.neatroots.instagramcloneapp.domain.model.Reel

interface GetReelRepo {
    suspend fun getOwnReel(): ArrayList<Reel>

    suspend fun getAllReel(): ArrayList<Reel>
}