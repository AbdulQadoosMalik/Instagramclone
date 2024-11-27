package com.neatroots.instagramcloneapp.domain.repo

import com.neatroots.instagramcloneapp.domain.model.Reel

interface CreateReelRepo {
    suspend fun createReel(
        reel: Reel,
        onProgress: (Int) -> Unit,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    )
}