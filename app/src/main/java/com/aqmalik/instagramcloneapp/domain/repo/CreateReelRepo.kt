package com.aqmalik.instagramcloneapp.domain.repo

import com.aqmalik.instagramcloneapp.domain.model.Reel

interface CreateReelRepo {
    suspend fun createReel(
        reel: Reel,
        onProgress: (Int) -> Unit,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    )
}