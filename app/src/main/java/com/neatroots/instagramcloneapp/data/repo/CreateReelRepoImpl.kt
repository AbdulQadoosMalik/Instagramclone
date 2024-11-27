package com.neatroots.instagramcloneapp.data.repo


import androidx.core.net.toUri
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.neatroots.instagramcloneapp.domain.model.Reel
import com.neatroots.instagramcloneapp.domain.repo.CreateReelRepo
import com.neatroots.instagramcloneapp.presentation.util.REEL
import com.neatroots.instagramcloneapp.presentation.util.REEL_FOLDER
import com.neatroots.instagramcloneapp.presentation.util.USER_NODE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class CreateReelRepoImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val firebaseStorage: FirebaseStorage
) : CreateReelRepo {

    override suspend fun createReel(
        reel: Reel,
        onProgress: (Int) -> Unit,  // Progress function now takes an Int parameter
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        try {
            // 1. Upload Video to Firebase Storage and get its URL
            val videoUrl = withContext(Dispatchers.IO) {
                reel.videoUri?.let { uri ->
                    val videoRef = firebaseStorage.getReference(REEL_FOLDER)
                        .child(UUID.randomUUID().toString())

                    // Monitor upload progress
                    val uploadTask = videoRef.putFile(uri.toUri())
                    uploadTask.addOnProgressListener { taskSnapshot ->
                        val progress =
                            (100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount).toInt()
                        onProgress(progress)  // Pass progress percentage to the callback
                    }.await()

                    // Get download URL after upload completes
                    videoRef.downloadUrl.await().toString()
                }
            }


            // 2. Create a new Reel object with the video URL included
            val updatedReel = videoUrl?.let { reel.copy(videoUri = it) }

            // 3. Store the updated Reel object in Firestore
            updatedReel?.let { newReel ->
                db.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid)
                    .get().addOnSuccessListener {
                        val reelRef = db.collection(REEL).document()
                        reelRef.set(newReel)
                            .addOnSuccessListener {
                                db.collection(Firebase.auth.currentUser!!.uid + REEL).document()
                                    .set(newReel)
                                    .addOnSuccessListener {
                                        onSuccess()
                                    }
                            }
                            .addOnFailureListener { exception -> onFailure(exception) }
                    }
            }

        } catch (e: Exception) {
            // Handle any errors that occurred during the process
            onFailure(e)
        }
    }
}
