package com.aqmalik.instagramcloneapp.presentation.profile

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.aqmalik.instagramcloneapp.R
import com.aqmalik.instagramcloneapp.common.resource.State
import com.aqmalik.instagramcloneapp.domain.model.Reel
import com.aqmalik.instagramcloneapp.presentation.component.ReelVideoPlayer
import com.aqmalik.instagramcloneapp.presentation.util.Const.MediumSpacerHeight
import com.aqmalik.instagramcloneapp.presentation.util.Const.RoundedImageSizeSmall
import com.aqmalik.instagramcloneapp.presentation.util.Const.SmallMediumSpacerHeight
import com.aqmalik.instagramcloneapp.presentation.viewModel.ICViewmodel


@Composable
fun ViewReelScreen(viewModel: ICViewmodel) {
    val state by viewModel.state.collectAsState()
    val reels by viewModel.reels.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getOwnReel()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white)),
        contentAlignment = Alignment.Center
    ) {
        when (state) {
            is State.Loading -> {
                /*CircularProgressIndicator()*/
            }

            is State.Success -> {
                ProfileReelSection(reels = reels)
            }

            is State.Error -> {
                val errorMessage = (state as State.Error).message
                Text(
                    text = errorMessage,
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
                )
            }

            else -> {
                Text(
                    text = "No Reels available",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}


@Composable
fun ProfileReelSection(reels: List<Reel>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(4.dp)
    ) {
        items(reels) { reel ->
            ReelItem(reel = reel)
        }
    }
}

@Composable
fun ReelItem(reel: Reel) {
    val context = LocalContext.current
    val showDialog = remember { mutableStateOf(false) }
    val thumbnail = remember { mutableStateOf<Bitmap?>(null) }

    // Load the video thumbnail asynchronously
    DisposableEffect(Unit) {
        val requestManager = Glide.with(context)
        requestManager
            .asBitmap()
            .load(reel.videoUri) // reel.videoUri is the link to the video
            .frame(1000000) // Fetch the frame at 1 second (adjust if necessary)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    thumbnail.value = resource
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
        onDispose { /* Clean up if needed */ }
    }

    Box(
        modifier = Modifier
            .padding(4.dp)
            .aspectRatio(9f / 16f)
            .clickable {
                showDialog.value = true
            },
        contentAlignment = Alignment.Center
    ) {
        thumbnail.value?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = reel.caption,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } ?: run {
            // You can show a placeholder image while the thumbnail is loading
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }

    // Show the dialog when showDialog is true
    if (showDialog.value) {
        ViewReelDialog(reel = reel)
    }
}


@Composable
fun ViewReelDialog(reel: Reel) {

    val openDialog = rememberSaveable { mutableStateOf(true) }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            containerColor = colorResource(id = R.color.white),
            title = {
                Text(text = "Reel Details")
            },
            text = {
                Column(
                    modifier = Modifier
                        .aspectRatio(9f / 18f)
                        .padding(16.dp)
                ) {
                    // Display the post image

                    val uri = Uri.parse(reel.videoUri)
                    ReelVideoPlayer(uri)

                    Spacer(modifier = Modifier.height(MediumSpacerHeight))
                    Row(verticalAlignment = Alignment.Bottom) {
                        Box(
                            modifier = Modifier
                                .size(RoundedImageSizeSmall)
                                .background(
                                    colorResource(id = R.color.lightWhite),
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            AsyncImage(
                                model = reel.profileLink,
                                contentDescription = reel.caption,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(50.dp))
                                    .background(
                                        colorResource(id = R.color.lightWhite),
                                        shape = CircleShape
                                    ),
                                contentScale = ContentScale.Crop,

                                )
                        }

                        Spacer(modifier = Modifier.width(SmallMediumSpacerHeight))
                        // Display the caption
                        Text(
                            text = reel.caption,
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 17.sp,
                            modifier = Modifier.padding(top = 0.dp)
                        )


                    }


                }
            },
            confirmButton = {
                OutlinedButton(onClick = { openDialog.value = false }) {
                    Text(text = "Close")
                }
            }
        )
    }
}


