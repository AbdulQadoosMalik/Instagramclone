package com.neatroots.instagramcloneapp.presentation.viewModel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neatroots.instagramcloneapp.common.resource.NavigationEvent
import com.neatroots.instagramcloneapp.common.resource.State
import com.neatroots.instagramcloneapp.domain.model.Post
import com.neatroots.instagramcloneapp.domain.model.Reel
import com.neatroots.instagramcloneapp.domain.model.UserData
import com.neatroots.instagramcloneapp.domain.model.ValidationResult
import com.neatroots.instagramcloneapp.domain.usecase.AuthUseCase
import com.neatroots.instagramcloneapp.domain.usecase.CreatePostUseCase
import com.neatroots.instagramcloneapp.domain.usecase.CreateReelUseCase
import com.neatroots.instagramcloneapp.domain.usecase.GetPostUseCase
import com.neatroots.instagramcloneapp.domain.usecase.GetReelUseCase
import com.neatroots.instagramcloneapp.domain.usecase.SearchUseCase
import com.neatroots.instagramcloneapp.domain.usecase.UserPrefUseCase
import com.neatroots.instagramcloneapp.presentation.navgraph.Routes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.koin.core.qualifier.named
import javax.inject.Inject


class ICViewmodel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val userPrefUseCase: UserPrefUseCase,
    private val createPostUseCase: CreatePostUseCase,
    private val createReelUseCase: CreateReelUseCase,
    private val getPostUseCase: GetPostUseCase,
    private val getReelUseCase: GetReelUseCase,
    private val searchUseCase: SearchUseCase
) : ViewModel() {

    private var _userData: UserData? = null
    val userData: UserData?
        get() = _userData


    private val _state = MutableStateFlow<State>(State.Idle)
    val state: StateFlow<State> = _state

    private val _navigationEvent = MutableSharedFlow<NavigationEvent?>()
    val navigationEvent = _navigationEvent.asSharedFlow()


    init {
        getUserDetails()
    }

    // Validation Email Name And Password Image
    fun validateName(name: String): ValidationResult {
        return if (name.isBlank()) {
            ValidationResult(false, "Name cannot be empty")
        } else {
            ValidationResult(true)
        }
    }

    fun validateEmail(email: String): ValidationResult {
        return if (email.isBlank()) {
            ValidationResult(false, "Email cannot be empty")
        } else {
            ValidationResult(true)
        }
    }

    fun validatePassword(password: String): ValidationResult {
        return if (password.isBlank()) {
            ValidationResult(false, "Password cannot be empty")
        } else {
            ValidationResult(true)
        }
    }

    fun validateImage(imageUri: Uri?): ValidationResult {
        return if (imageUri == null) {
            ValidationResult(false, "Please select a profile image")
        } else {
            ValidationResult(true)
        }
    }


    // Auto Login
    fun autoLogin() {
        viewModelScope.launch {
            val credentials = userPrefUseCase.loadCredentials()

            if (credentials != null) {
                val (savedEmail, savedPassword) = credentials
                if (savedEmail.isNotEmpty() && savedPassword.isNotEmpty()) {
                    login(userData = UserData(email = savedEmail, password = savedPassword))
                } else {
                    _state.value = State.Idle
                }
            } else {
                _state.value = State.Idle
            }
        }
    }

    // SignUp
    fun signUp(userData: UserData) {
        viewModelScope.launch {
            _state.value = State.Loading
            val result = authUseCase.signUpWithEmailAndPassword(userData)
            if (result != null) {
                if (result.isSuccess) {
                    userPrefUseCase.saveCredentials(
                        email = userData.email,
                        password = userData.password
                    )
                    _state.value = State.Success
                    _navigationEvent.emit(NavigationEvent.NavigateToRoute(Routes.Home.route))
                } else {
                    _state.value = State.Error(result.exceptionOrNull()?.message ?: "Unknown error")
                    _navigationEvent.emit(
                        NavigationEvent.ShowError(
                            result.exceptionOrNull()?.message ?: "Unknown error"
                        )
                    )
                }
            }
        }
    }

    // Login
    fun login(userData: UserData) {
        viewModelScope.launch {
            _state.value = State.Loading
            val result = authUseCase.signInWithEmailAndPassword(userData)

            if (result.isSuccess) {
                userPrefUseCase.saveCredentials(
                    email = userData.email,
                    password = userData.password
                )
                _state.value = State.Success
                _navigationEvent.emit(NavigationEvent.NavigateToRoute(Routes.Home.route))
            } else {
                val errorMessage = result.exceptionOrNull()?.message ?: "Unknown error"
                _state.value = State.Error(errorMessage)
                _navigationEvent.emit(NavigationEvent.ShowError(errorMessage))
            }
        }
    }

    // Update
    fun update(name: String, email: String, password: String, profileImage: Uri?) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = State.Loading

            val loadedCredentials = userPrefUseCase.loadCredentials()
            if (loadedCredentials == null) {
                _state.value = State.Error("No credentials found")
                return@launch
            }

            val (storedEmail, storedPassword) = loadedCredentials
            if (email != storedEmail || password != storedPassword) {
                _state.value = State.Error("Credentials do not match")
                return@launch
            }

            val result = authUseCase.updateUser(
                UserData(
                    name = name,
                    email = email,
                    password = password,
                    profileImageUrl = profileImage
                )
            )

            if (result.isSuccess) {
                _state.value = State.Success
                _navigationEvent.emit(NavigationEvent.NavigateToRoute(Routes.Home.route))
            } else {
                val errorMessage = result.exceptionOrNull()?.message ?: "Unknown error"
                _state.value = State.Error(errorMessage)
                _navigationEvent.emit(NavigationEvent.ShowError(errorMessage))
            }
        }

    }


    // get User


    fun getUserDetails() {
        viewModelScope.launch {
            _state.value = State.Loading
            try {
                val result = authUseCase.getUserDetails()
                if (result.isSuccess) {
                    _userData = result.getOrNull()
                    _state.value = State.Success
                } else {
                    _state.value = State.Error(result.exceptionOrNull()?.message ?: "Unknown error")
                }
            } catch (e: Exception) {
                _state.value = State.Error(e.message ?: "Unknown error")
            }
        }
    }

    // create post
    fun createPost(postUrl: String, caption: String, uid: String, time: String) {
        viewModelScope.launch {
            _state.value = State.Loading

            // Convert Uri to String if it's not null
            val post = Post(
                postUrl = postUrl,
                caption = caption,
                uid = uid,
                time = time
            )

            try {
                createPostUseCase.createPost(
                    post,
                    onSuccess = {
                        viewModelScope.launch {
                            _state.value = State.Success
                            _navigationEvent.emit(NavigationEvent.NavigateToRoute(Routes.Home.route))
                        }
                    },
                    onFailure = { exception ->
                        viewModelScope.launch {
                            _state.value = State.Error(exception.message ?: "Unknown error")
                            _navigationEvent.emit(
                                NavigationEvent.ShowError(
                                    exception.message ?: "Unknown error"
                                )
                            )
                        }
                    }
                )
            } catch (e: Exception) {
                _state.value = State.Error(e.message ?: "Unknown error")
                _navigationEvent.emit(NavigationEvent.ShowError(e.message ?: "Unknown error"))
            }
        }
    }

    // create reel
     fun createReel(videoUri: String, caption: String) {
         viewModelScope.launch {
             _state.value = State.Loading

             try {
                 getUserDetails()
                 val reel = Reel(
                     videoUri = videoUri,
                     caption = caption,
                     profileLink = userData?.profileImageUrl.toString()
                 )

                 createReelUseCase.createReel(
                     reel,
                     onProgress = { progress ->
                         // Update the progress state
                         _state.value = State.Progress(progress)
                     },
                     onSuccess = {
                         viewModelScope.launch {
                             _state.value = State.Success
                             _navigationEvent.emit(NavigationEvent.NavigateToRoute(Routes.Home.route))
                         }
                     },
                     onFailure = { exception ->
                         viewModelScope.launch {
                             _state.value = State.Error(exception.message ?: "Unknown error")
                             _navigationEvent.emit(
                                 NavigationEvent.ShowError(exception.message ?: "Unknown error")
                             )
                         }
                     }
                 )
             } catch (e: Exception) {
                 _state.value = State.Error(e.message ?: "Unknown error")
                 _navigationEvent.emit(NavigationEvent.ShowError(e.message ?: "Unknown error"))
             }
         }
     }

    // getOwn Post
    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts

    fun getOwnPost() {
        viewModelScope.launch {
            _state.value = State.Loading
            try {
                val postList = getPostUseCase.getOwnPost()
                if (postList.isEmpty()) {
                    _state.value = State.Error("No Posts available")
                } else {
                    _posts.value = postList // Update the posts list
                    _state.value = State.Success
                }
            } catch (e: Exception) {
                _state.value = State.Error(e.message ?: "Failed to fetch posts")
                _navigationEvent.emit(NavigationEvent.ShowError(e.message ?: "Unknown error"))
            }
        }
    }

    // getOwn Reel
    private val _reels = MutableStateFlow<List<Reel>>(emptyList())
    val reels: StateFlow<List<Reel>> = _reels

    fun getOwnReel() {
        viewModelScope.launch {
            _state.value = State.Loading
            try {
                val reelList = getReelUseCase.getOwnReel()
                if (reelList.isEmpty()) {
                    _state.value = State.Error("No Reels available")
                } else {
                    _reels.value = reelList
                    _state.value = State.Success
                }
            } catch (e: Exception) {
                _state.value = State.Error(e.message ?: "Failed to fetch posts")
                _navigationEvent.emit(NavigationEvent.ShowError(e.message ?: "Unknown error"))
            }
        }
    }




    // getAll Reel
    private val _allReels = MutableStateFlow<List<Reel>>(emptyList())
    val allReels: StateFlow<List<Reel>> = _allReels

    fun getAllReel() {
        viewModelScope.launch {
            _state.value = State.Loading
            try {
                val reelList = getReelUseCase.getAllReel()
                if (reelList.isEmpty()) {
                    _state.value = State.Error("No Reels available")
                } else {
                    _allReels.value = reelList
                    _state.value = State.Success
                }
            } catch (e: Exception) {
                _state.value = State.Error(e.message ?: "Failed to fetch posts")
                _navigationEvent.emit(NavigationEvent.ShowError(e.message ?: "Unknown error"))
            }
        }
    }



    // getAll Post
    private val _allPosts = MutableStateFlow<List<Post>>(emptyList())
    val allPosts: StateFlow<List<Post>> = _allPosts

    fun getAllPost() {
        viewModelScope.launch {
            _state.value = State.Loading
            try {
                val postList = getPostUseCase.getAllPost()
                if (postList.isEmpty()) {
                    _state.value = State.Error("No Posts available")
                } else {
                    _allPosts.value = postList // Update the posts list
                    _state.value = State.Success
                }
            } catch (e: Exception) {
                _state.value = State.Error(e.message ?: "Failed to fetch posts")
                _navigationEvent.emit(NavigationEvent.ShowError(e.message ?: "Unknown error"))
            }
        }
    }






    // getAll User through search
    private val _searchResult = MutableStateFlow<List<UserData>>(emptyList())
    val searchResult: StateFlow<List<UserData>> = _searchResult

    fun search(name: String) {
        viewModelScope.launch {
            _state.value = State.Loading
            try {
                val searchList = searchUseCase.search(name = name)
                if (searchList.isEmpty()) {
                    _state.value = State.Error("No Posts available")
                } else {
                    Log.d("SeachRes", "search: $searchList")
                    _searchResult.value = searchList // Update the posts list
                    _state.value = State.Success
                }
            } catch (e: Exception) {
                _state.value = State.Error(e.message ?: "Failed to fetch posts")
                _navigationEvent.emit(NavigationEvent.ShowError(e.message ?: "Unknown error"))
            }
        }
    }




}









