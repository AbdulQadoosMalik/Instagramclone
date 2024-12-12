package com.aqmalik.instagramcloneapp.data.crypto

import android.content.Context
import com.aqmalik.instagramcloneapp.domain.crypto.EncryptionManager
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import java.security.Key
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import android.util.Base64
import javax.inject.Inject

class EncryptionManagerImpl @Inject constructor(private val context: Context) : EncryptionManager {

    private val ALGORITHM = "AES"
    private val TRANSFORMATION = "AES/CBC/PKCS7Padding"

    private fun getSecretKey(): Key {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        val encryptedSharedPreferences = EncryptedSharedPreferences.create(
            "secret_shared_prefs",
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        val secretKeyString = encryptedSharedPreferences.getString("secretKey", null)
        return if (secretKeyString == null) {
            val secretKey = generateKey()
            encryptedSharedPreferences.edit()
                .putString("secretKey", Base64.encodeToString(secretKey.encoded, Base64.DEFAULT))
                .apply()
            secretKey
        } else {
            val decodedKey = Base64.decode(secretKeyString, Base64.DEFAULT)
            SecretKeySpec(decodedKey, 0, decodedKey.size, ALGORITHM)
        }
    }

    private fun generateKey(): Key {
        val keyGenerator = KeyGenerator.getInstance(ALGORITHM)
        keyGenerator.init(256)
        return keyGenerator.generateKey()
    }

    override fun encrypt(data: String): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey())
        val iv = cipher.iv
        val encryptedValue = cipher.doFinal(data.toByteArray(Charsets.UTF_8))
        return Base64.encodeToString(iv + encryptedValue, Base64.DEFAULT)
    }

    override fun decrypt(data: String): String {
        val encryptedBytesWithIv = Base64.decode(data, Base64.DEFAULT)
        val iv = encryptedBytesWithIv.copyOfRange(0, 16)
        val encryptedBytes = encryptedBytesWithIv.copyOfRange(16, encryptedBytesWithIv.size)
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(), IvParameterSpec(iv))
        val decryptedValue = cipher.doFinal(encryptedBytes)
        return String(decryptedValue, Charsets.UTF_8)
    }
}
