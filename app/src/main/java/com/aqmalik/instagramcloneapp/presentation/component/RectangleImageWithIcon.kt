package com.aqmalik.instagramcloneapp.presentation.component


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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.aqmalik.instagramcloneapp.R
import com.aqmalik.instagramcloneapp.presentation.util.Const

@Composable
fun RectangleImageWithIcon(onImageSelected: (Uri?) -> Unit) {

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
            .size(Const.RectangleImageWidth, Const.RectangleImageHeight)
            .background(colorResource(id = R.color.lightWhite), shape = RoundedCornerShape(15.dp)),
        contentAlignment = Alignment.Center
    ) {
        if (bitmap != null) {
            Image(
                bitmap = bitmap,
                contentDescription = "SelectedImage",
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            Icon(
                imageVector = Icons.Default.Image,  // Ensure you have this vector in your resources
                contentDescription = "DefaultImage",
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        launcher.launch("image/*")
                    }
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
