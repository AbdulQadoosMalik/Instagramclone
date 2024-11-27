package com.neatroots.instagramcloneapp.presentation.reels

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.neatroots.instagramcloneapp.R
import com.neatroots.instagramcloneapp.common.resource.State
import com.neatroots.instagramcloneapp.domain.model.Reel
import com.neatroots.instagramcloneapp.presentation.component.BottomNavigationScaffold
import com.neatroots.instagramcloneapp.presentation.component.ReelVideoPlayer
import com.neatroots.instagramcloneapp.presentation.profile.PostItem
import com.neatroots.instagramcloneapp.presentation.util.Const.RoundedImageSizeSmall
import com.neatroots.instagramcloneapp.presentation.util.Const.SmallMediumSpacerHeight
import com.neatroots.instagramcloneapp.presentation.viewModel.ICViewmodel


@Composable
fun ReelsScreen(navController: NavHostController, viewModel: ICViewmodel) {
    val state by viewModel.state.collectAsState()
    val allReels: List<Reel> by viewModel.allReels.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getAllReel()
    }

    BottomNavigationScaffold(navController = navController) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (state) {
                is State.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is State.Success -> {
                    EachReelSection(
                        reels = allReels
                    )
                }

                is State.Error -> {
                    val errorMessage = (state as State.Error).message
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = errorMessage,
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                else -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "No Reels available",
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center
                        )
                    }

                }
            }
        }
    }
}


@Composable
fun EachReelSection(
    reels: List<Reel>,
) {

    if (reels.isNotEmpty()) {

        LazyColumn {

            itemsIndexed(reels) { _, reel ->
                Box(
                    modifier = Modifier.fillParentMaxSize(),
                ) {

                    val uri = Uri.parse(reel.videoUri)

                    ReelVideoPlayer(uri = uri)

                    Column(
                        modifier = Modifier.align(Alignment.BottomStart),
                    ) {
                        ReelFooter(reel = reel)
                        Divider()
                    }
                }
            }
        }


    } else {
        // Handle empty reels case
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = "No Reels available",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}


@Composable
private fun ReelFooter(
    reel: Reel
) {
    Column(
        modifier = Modifier.padding(10.dp),
        verticalArrangement = Arrangement.Center
    ) {
        // User data
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Profile image
            val profilePainter = rememberImagePainter(
                data = reel.profileLink,
                builder = {
                    crossfade(true)
                    placeholder(R.drawable.user_profile) // Use a placeholder or default image
                }
            )

            Image(
                painter = profilePainter,
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(40.dp) // Adjust size as needed
                    .background(color = Color.Gray, shape = CircleShape)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(8.dp))

            // User information
            Column {
                Text(
                    text = reel.caption,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

