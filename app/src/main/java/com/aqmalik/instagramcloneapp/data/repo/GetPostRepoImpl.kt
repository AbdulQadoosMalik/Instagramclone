package com.aqmalik.instagramcloneapp.data.repo


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.aqmalik.instagramcloneapp.domain.model.Post
import com.aqmalik.instagramcloneapp.domain.repo.GetPostRepo
import com.aqmalik.instagramcloneapp.presentation.util.POST
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

class GetPostRepoImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : GetPostRepo {
    override suspend fun getOwnPost(): ArrayList<Post> {
        val postList = ArrayList<Post>()

        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            try {
                val snapshot = firestore.collection(currentUser.uid).get().await()
                for (document in snapshot.documents) {
                    val post = document.toObject<Post>()
                        ?: return ArrayList() // Return an empty ArrayList if any Post is null
                    postList.add(post)
                }
            } catch (e: Exception) {
                // Handle the error, e.g., log it or return an empty list
                return ArrayList()
            }
        }
        return postList
    }

    override suspend fun getAllPost(): ArrayList<Post> {

        val postList = ArrayList<Post>()

        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            try {
                val snapshot = firestore.collection(POST).get().await()
                for (document in snapshot.documents) {
                    val post = document.toObject<Post>()
                        ?: return ArrayList() // Return an empty ArrayList if any Post is null
                    postList.add(post)
                }
            } catch (e: Exception) {
                // Handle the error, e.g., log it or return an empty list
                return ArrayList()
            }
        }
        return postList

    }
}
