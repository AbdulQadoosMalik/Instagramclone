package com.neatroots.instagramcloneapp.data.repo

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.neatroots.instagramcloneapp.domain.crypto.EncryptionManager
import com.neatroots.instagramcloneapp.domain.repo.UserPrefRepo
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UserPrefRepoImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val encryptionManager: EncryptionManager
) : UserPrefRepo {

    private val emailKey = stringPreferencesKey("email")
    private val passwordKey = stringPreferencesKey("password")

    override suspend fun saveCredentials(email: String, password: String) {
        val encryptedEmail = encryptionManager.encrypt(email)
        val encryptedPassword = encryptionManager.encrypt(password)

        dataStore.edit { settings ->
            settings[emailKey] = encryptedEmail
            settings[passwordKey] = encryptedPassword
        }
    }

    override suspend fun loadCredentials(): Pair<String, String>? {
        val preferences = dataStore.data.first()
        return preferences[emailKey]?.let { encryptedEmail ->
            preferences[passwordKey]?.let { encryptedPassword ->
                val email = encryptionManager.decrypt(encryptedEmail)
                val password = encryptionManager.decrypt(encryptedPassword)
                Pair(email, password)
            }
        }
    }
}
