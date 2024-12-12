package com.aqmalik.instagramcloneapp.presentation.loginsignup.component

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import com.aqmalik.instagramcloneapp.R
import com.aqmalik.instagramcloneapp.presentation.util.Const.RoundedImageSize

@Composable
fun RoundedImageWithIcon(onImageSelected: (Uri?) -> Unit) {

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    // Image picker launcher
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
        onImageSelected(uri)
    }

    // Load bitmap from URI if selected, otherwise null
    val bitmap: ImageBitmap? = selectedImageUri?.let { uri ->
        loadBitmapFromUri(context, uri)
    }

    Box(
        modifier = Modifier
            .size(RoundedImageSize)
            .background(colorResource(id = R.color.lightWhite), shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        if (bitmap != null) {
            Image(
                bitmap = bitmap,
                contentDescription = "ProfileImage",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        } else {
            // Display default vector image when no image is selected
            Image(
                painter = painterResource(id = R.drawable.user_icon), // Ensure you have this vector in your resources
                contentDescription = "DefaultProfileImage",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape).clickable {
                        launcher.launch("image/*")
                    },
                contentScale = ContentScale.Crop
            )
        }

    }
}

@Composable
private fun loadBitmapFromUri(context: android.content.Context, uri: Uri): ImageBitmap? {
    return context.contentResolver.openInputStream(uri)?.use { inputStream ->
        BitmapFactory.decodeStream(inputStream)?.asImageBitmap()
    }
}


