package com.aqmalik.instagramcloneapp.domain.usecase

import com.aqmalik.instagramcloneapp.domain.model.UserData
import com.aqmalik.instagramcloneapp.domain.repo.SearchRepo
import javax.inject.Inject

class SearchUseCase @Inject constructor(private val searchRepo: SearchRepo) : SearchRepo {
    override suspend fun search(name: String): ArrayList<UserData> {
        return searchRepo.search(name)
    }
}