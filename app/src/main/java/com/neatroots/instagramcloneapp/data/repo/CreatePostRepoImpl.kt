package com.neatroots.instagramcloneapp.data.repo

import androidx.core.net.toUri
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.neatroots.instagramcloneapp.domain.model.Post
import com.neatroots.instagramcloneapp.domain.repo.CreatePostRepo
import com.neatroots.instagramcloneapp.presentation.util.POST
import com.neatroots.instagramcloneapp.presentation.util.POST_FOLDER
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class CreatePostRepoImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val firebaseStorage: FirebaseStorage
) : CreatePostRepo {
    override suspend fun createPost(
        post: Post,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        try {
            // 1. Upload Image to Firebase Storage and get its URL
            val imageUrl = post.postUrl.let { uri ->
                val imageRef = firebaseStorage.getReference(POST_FOLDER)
                    .child(UUID.randomUUID().toString())
                val uploadTask = imageRef.putFile(uri.toUri()).await()
                uploadTask.storage.downloadUrl.await().toString()
            }

            // 2. Create a new Post object with the image URL included
            val updatedPost = imageUrl.let { post.copy(postUrl = it) }

            // 3. Store the updated Post object in Firestore
            val postRef = db.collection(POST).document()
            postRef.set(updatedPost)
                .addOnSuccessListener {
                    db.collection(Firebase.auth.currentUser!!.uid).document()
                        .set(updatedPost)
                        .addOnSuccessListener {
                            onSuccess()
                        }
                }
                .addOnFailureListener { exception -> onFailure(exception) }


        } catch (e: Exception) {
            // Handle any errors that occurred during the process
            onFailure(e)
        }
    }
}
