package com.neatroots.instagramcloneapp.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.neatroots.instagramcloneapp.R
import com.neatroots.instagramcloneapp.common.resource.State
import com.neatroots.instagramcloneapp.domain.model.Post
import com.neatroots.instagramcloneapp.presentation.viewModel.ICViewmodel
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.draw.clip
import com.neatroots.instagramcloneapp.presentation.util.Const.RoundedImageSize
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun ViewPostScreen(viewModel: ICViewmodel) {
    val state by viewModel.state.collectAsState()
    val posts by viewModel.posts.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getOwnPost()
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
                ProfilePostSection(posts = posts)
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
                    text = "No posts available",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}


@Composable
fun ProfilePostSection(posts: List<Post>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(4.dp)
    ) {
        items(posts) { post ->
            PostItem(post = post)
        }
    }
}

@Composable
fun PostItem(post: Post) {
    val showDialog = remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .padding(4.dp)
            .aspectRatio(3f / 4f)
            .clickable {
                showDialog.value = true
            },
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = post.postUrl,
            contentDescription = post.caption,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
    // Show the dialog when showDialog is true
    if (showDialog.value) {
        ViewPostDialog(post = post)
    }
}


@Composable
fun ViewPostDialog(post: Post) {
    val formattedTime = rememberSaveable(post.time) {
        val sdf = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
        val date = Date(post.time.toLong()) // Convert the string to long
        sdf.format(date)
    }

    val openDialog = rememberSaveable { mutableStateOf(true) }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            containerColor = colorResource(id = R.color.white),
            title = {
                Text(text = "Post Details")
            },
            text = {
                Column(
                    modifier = Modifier
                        .aspectRatio(3f / 4.5f)
                        .padding(16.dp)
                ) {
                    // Display the post image

                    AsyncImage(
                        model = post.postUrl,
                        contentDescription = post.caption,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .aspectRatio(3f / 4f),
                        contentScale = ContentScale.Crop
                    )


                    Spacer(modifier = Modifier.height(16.dp))

                    // Display the caption
                    Text(
                        text = post.caption,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    // Display the time
                    Text(
                        text = formattedTime,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
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

