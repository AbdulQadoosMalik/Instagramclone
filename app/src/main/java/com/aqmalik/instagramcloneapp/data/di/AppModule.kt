package com.aqmalik.instagramcloneapp.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.aqmalik.instagramcloneapp.data.crypto.EncryptionManagerImpl
import com.aqmalik.instagramcloneapp.data.repo.AuthRepoImpl
import com.aqmalik.instagramcloneapp.data.repo.CreatePostRepoImpl
import com.aqmalik.instagramcloneapp.data.repo.CreateReelRepoImpl
import com.aqmalik.instagramcloneapp.data.repo.GetPostRepoImpl
import com.aqmalik.instagramcloneapp.data.repo.GetReelRepoImpl
import com.aqmalik.instagramcloneapp.data.repo.SearchRepoImpl
import com.aqmalik.instagramcloneapp.data.repo.UserPrefRepoImpl
import com.aqmalik.instagramcloneapp.domain.crypto.EncryptionManager
import com.aqmalik.instagramcloneapp.domain.repo.AuthRepo
import com.aqmalik.instagramcloneapp.domain.repo.CreatePostRepo
import com.aqmalik.instagramcloneapp.domain.repo.CreateReelRepo
import com.aqmalik.instagramcloneapp.domain.repo.GetPostRepo
import com.aqmalik.instagramcloneapp.domain.repo.GetReelRepo
import com.aqmalik.instagramcloneapp.domain.repo.SearchRepo
import com.aqmalik.instagramcloneapp.domain.repo.UserPrefRepo
import com.aqmalik.instagramcloneapp.domain.usecase.AuthUseCase
import com.aqmalik.instagramcloneapp.domain.usecase.CreatePostUseCase
import com.aqmalik.instagramcloneapp.domain.usecase.CreateReelUseCase
import com.aqmalik.instagramcloneapp.domain.usecase.GetPostUseCase
import com.aqmalik.instagramcloneapp.domain.usecase.GetReelUseCase
import com.aqmalik.instagramcloneapp.domain.usecase.SearchUseCase
import com.aqmalik.instagramcloneapp.domain.usecase.UserPrefUseCase
import com.aqmalik.instagramcloneapp.presentation.viewModel.ICViewmodel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

val appModule = module {

    // Firebase instance
    single<FirebaseAuth> { FirebaseAuth.getInstance() }
    single<FirebaseStorage> { FirebaseStorage.getInstance() }
    single<FirebaseFirestore> { FirebaseFirestore.getInstance() }

    // Auth instance
    single<AuthRepo> { AuthRepoImpl(get(), get(), get()) }
    single { AuthUseCase(get()) }


    // Search instance
    single<SearchRepo> { SearchRepoImpl(get(), get()) }
    single { SearchUseCase(get()) }

    // Post instance
    single<CreatePostRepo> { CreatePostRepoImpl(get(), get()) }
    single { CreatePostUseCase(get()) }


    // GetPost instance
    single<GetPostRepo> { GetPostRepoImpl(get(), get()) }
    single { GetPostUseCase(get()) }


    // Reel instance
    single<CreateReelRepo> { CreateReelRepoImpl(get(), get()) }
    single { CreateReelUseCase(get()) }


    // GetPost instance
    single<GetReelRepo> { GetReelRepoImpl(get(), get()) }
    single { GetReelUseCase(get()) }


    // EncryptionManager instance
    single<EncryptionManager> { EncryptionManagerImpl(androidContext()) }

    // User Pref instance
    single<DataStore<Preferences>> {
        val context: Context = androidContext()
        context.dataStore
    }
    single<UserPrefRepo> { UserPrefRepoImpl(get(), get()) }
    single { UserPrefUseCase(get()) }

    // ViewModel
    viewModel { ICViewmodel(get(), get(), get(), get(), get(), get(), get()) }

}
