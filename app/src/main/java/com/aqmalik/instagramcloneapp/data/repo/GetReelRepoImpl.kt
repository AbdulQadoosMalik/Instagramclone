package com.aqmalik.instagramcloneapp.data.repo

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.aqmalik.instagramcloneapp.domain.model.Reel
import com.aqmalik.instagramcloneapp.domain.repo.GetReelRepo
import com.aqmalik.instagramcloneapp.presentation.util.REEL
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetReelRepoImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : GetReelRepo {

    override suspend fun getOwnReel(): ArrayList<Reel> {

        val reelList = ArrayList<Reel>()
        val currentUser = firebaseAuth.currentUser

        if (currentUser != null) {
            try {
                val snapshot = firestore.collection(currentUser.uid + REEL).get().await()
                for (document in snapshot.documents) {
                    val reel = document.toObject<Reel>()
                        ?: return ArrayList() // Return an empty ArrayList if any Post is null
                    reelList.add(reel)
                }
            } catch (e: Exception) {
                // Handle the error, e.g., log it or return an empty list
                return ArrayList()
            }
        }
        return reelList
    }

    override suspend fun getAllReel(): ArrayList<Reel> {
        val reelList = ArrayList<Reel>()

        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            try {
                val snapshot = firestore.collection(REEL).get().await()
                for (document in snapshot.documents) {
                    val reel = document.toObject<Reel>()
                        ?: return ArrayList()
                    reelList.add(reel)
                }
            } catch (e: Exception) {
                return ArrayList()
            }
        }

        return reelList
    }

}



