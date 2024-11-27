package com.neatroots.instagramcloneapp.data.repo

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.neatroots.instagramcloneapp.domain.model.UserData
import com.neatroots.instagramcloneapp.domain.repo.SearchRepo
import com.neatroots.instagramcloneapp.presentation.util.USER_NODE
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SearchRepoImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : SearchRepo {

    // Temporary data class matching Firestore structure
    private data class FirestoreUserData(
        val name: String? = "",
        val email: String = "",
        val password: String = "",
        val profileImageUrl: String? = null
    )

    override suspend fun search(name: String): ArrayList<UserData> {
        val userList = ArrayList<UserData>()
        val currentUser = firebaseAuth.currentUser

        if (currentUser != null) {
            try {
                val snapshot = firestore.collection(USER_NODE).get().await()

                for (document in snapshot.documents) {
                    // Deserialize into the temporary class *inside* the loop
                    val tempUser = document.toObject<FirestoreUserData>()

                    // Create UserData, handling potential nulls and Uri parsing
                    val user = tempUser?.let {
                        try {
                            UserData(
                                name = it.name,
                                email = it.email,
                                password = it.password,
                                profileImageUrl = it.profileImageUrl?.let { Uri.parse(it) }
                            )
                        } catch (e: Exception) {
                            Log.w("SearchRes", "Invalid Uri: ${it.profileImageUrl}, skipping...", e)
                            null // Return null if Uri parsing fails
                        }
                    }

                    // Add non-null users to the list
                    user?.let { userList.add(it) }
                }
            } catch (e: Exception) {
                Log.e("SearchRepoImpl", "Error fetching users: ${e.message}")
            }
        }
        return userList
    }
}