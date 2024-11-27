package com.neatroots.instagramcloneapp.domain.model

data class ValidationResult(
    val isValid: Boolean,
    val errorMessage: String? = null
)