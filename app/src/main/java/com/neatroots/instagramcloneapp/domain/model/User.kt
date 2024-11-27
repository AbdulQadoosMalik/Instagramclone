package com.neatroots.instagramcloneapp.domain.model

import android.net.Uri

data class UserData(
    val name: String? = "",
    val email: String = "",
    val password: String = "",
    val profileImageUrl: Uri? = null
)
