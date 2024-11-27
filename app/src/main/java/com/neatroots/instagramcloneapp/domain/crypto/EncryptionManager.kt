package com.neatroots.instagramcloneapp.domain.crypto

interface EncryptionManager {
    fun encrypt(data: String): String
    fun decrypt(data: String): String
}
