package com.aqmalik.instagramcloneapp.data.repo

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.aqmalik.instagramcloneapp.domain.model.UserData
import com.aqmalik.instagramcloneapp.domain.repo.AuthRepo
import com.aqmalik.instagramcloneapp.presentation.util.USER_NODE
import com.aqmalik.instagramcloneapp.presentation.util.USER_PROFILE_FOLDER
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class AuthRepoImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseStorage: FirebaseStorage,
    private val firestore: FirebaseFirestore
) : AuthRepo {

    override suspend fun signUpWithEmailAndPassword(
        userData: UserData
    ): Result<Unit> {
        return try {
            // 1. Create User with Email and Password
            val authResult =
                firebaseAuth.createUserWithEmailAndPassword(userData.email, userData.password)
                    .await()
            val user = authResult.user ?: throw Exception("User creation failed")

            // 2. Upload Image to Firebase Storage
            val imageUrl = userData.profileImageUrl?.let { uri ->
                val imageRef = firebaseStorage.getReference(USER_PROFILE_FOLDER)
                    .child(UUID.randomUUID().toString())
                val uploadTask = imageRef.putFile(uri).await()
                uploadTask.storage.downloadUrl.await().toString()
            }

            // 3. Update User Profile with Name and Image URL
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(userData.name)
                .setPhotoUri(imageUrl?.let { Uri.parse(it) })
                .build()
            user.updateProfile(profileUpdates).await()

            // 4. Store User info in Firestore, including the image URL
            val updatedUserData = userData.copy(profileImageUrl = imageUrl?.let { Uri.parse(it) })
            firestore.collection(USER_NODE).document(user.uid).set(updatedUserData).await()

            Log.d("DB", "User created successfully")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("DB", "Error creating user", e)
            Result.failure(e)
        }
    }


    // Login User
    override suspend fun signInWithEmailAndPassword(userData: UserData): Result<Unit> {
        return try {
            firebaseAuth.signInWithEmailAndPassword(userData.email, userData.password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Update User
    override suspend fun updateUser(userData: UserData): Result<Unit> {
        return try {
            val firebaseUser = firebaseAuth.currentUser ?: throw Exception("User not logged in")

            // 1. Update Email (if changed)
            if (firebaseUser.email != userData.email) {
                firebaseUser.verifyBeforeUpdateEmail(userData.email).await()
            }

            // 2. Update Password (if changed)
            if (userData.password.isNotBlank()) {
                firebaseUser.updatePassword(userData.password).await()
            }

            // 3. Upload New Image (if provided)
            val newImageUrl = userData.profileImageUrl?.let { uri ->
                val imageRef =
                    firebaseStorage.reference.child("profile_images/${firebaseUser.uid}.jpg")
                val uploadTask = imageRef.putFile(uri).await()
                uploadTask.storage.downloadUrl.await().toString()
            }

            // 4. Update User Profile (name and image)
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(userData.name)
                .setPhotoUri(newImageUrl?.let { Uri.parse(it) } ?: firebaseUser.photoUrl)
                .build()
            firebaseUser.updateProfile(profileUpdates).await()

            // 5. Store Updated User info in Firestore, including the new image URL
            val updatedUserData =
                userData.copy(profileImageUrl = newImageUrl?.let { Uri.parse(it) })
            firestore.collection(USER_NODE).document(firebaseUser.uid).set(updatedUserData).await()

            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("DB", "Error updating user", e)
            Result.failure(e)
        }
    }


    // Get User Data
    override suspend fun getUserDetails(): Result<UserData?> {
        return try {
            // 1. Get the currently logged-in user
            val firebaseUser = firebaseAuth.currentUser ?: throw Exception("User not logged in")

            // 2. Extract user details
            val name = firebaseUser.displayName
            val email = firebaseUser.email
            val profileImageUrl = firebaseUser.photoUrl?.toString()

            // 3. Create a UserData object to hold the user's information
            val userData = UserData(
                name = name,
                email = email ?: "",
                profileImageUrl = profileImageUrl?.let { Uri.parse(it) },
                password = "" // Do not include password for security reasons
            )

            // 4. Return the user data
            Result.success(userData)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}



