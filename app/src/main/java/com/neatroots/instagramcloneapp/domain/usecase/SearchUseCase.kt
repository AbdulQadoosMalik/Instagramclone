package com.neatroots.instagramcloneapp.domain.usecase

import android.util.Log
import com.neatroots.instagramcloneapp.domain.model.UserData
import com.neatroots.instagramcloneapp.domain.repo.SearchRepo
import javax.inject.Inject

class SearchUseCase @Inject constructor(private val searchRepo: SearchRepo) : SearchRepo {
    override suspend fun search(name: String): ArrayList<UserData> {
        return searchRepo.search(name)
    }
}