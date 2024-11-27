package com.neatroots.instagramcloneapp.presentation.home

import android.content.Context
import android.content.Intent
import android.text.format.DateUtils
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.neatroots.instagramcloneapp.R
import com.neatroots.instagramcloneapp.common.resource.State
import com.neatroots.instagramcloneapp.presentation.component.BottomNavigationScaffold
import com.neatroots.instagramcloneapp.presentation.viewModel.ICViewmodel
import java.util.*

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ICViewmodel
) {

    val context = LocalContext.current

    val state by viewModel.state.collectAsState()
    val allPosts by viewModel.allPosts.collectAsState()


    var imageUri by remember { mutableStateOf("") }



    LaunchedEffect(state) {
        if (state is State.Success) {
            val userData = viewModel.userData
            if (userData != null) {
                imageUri = (userData.profileImageUrl ?: "").toString()
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getUserDetails()
        viewModel.getAllPost()
    }

    BottomNavigationScaffold(navController = navController) { innerpadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerpadding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(65.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(color = colorResource(id = R.color.lightWhite))
            ) {
                Text(
                    text = "Instagram",
                    fontSize = 29.sp,
                    modifier = Modifier.padding(top = 15.dp, start = 5.dp),
                    color = colorResource(
                        id = R.color.black
                    )
                )

                Image(
                    painter = painterResource(id = R.drawable.heart),
                    contentDescription = "null",
                    modifier = Modifier
                        .padding(start = 120.dp)
                        .size(40.dp)
                        .padding(top = 10.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.send),
                    contentDescription = "null",
                    modifier = Modifier
                        .padding(start = 6.dp)
                        .size(40.dp)
                        .padding(top = 10.dp)
                )

            }


            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                val painter = if (imageUri != "") {
                    rememberAsyncImagePainter(imageUri)
                } else {
                    painterResource(R.drawable.user_icon) // Replace with your actual placeholder image resource
                }

                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

            }

            HorizontalDivider(color = colorResource(id = R.color.lightWhite), thickness = 2.dp)

            LazyColumn {
                items(allPosts) { post ->
                    val timeAgo = rememberSaveable(post.time) {
                        val now = System.currentTimeMillis()
                        val postTime = post.time.toLong()
                        DateUtils.getRelativeTimeSpanString(
                            postTime,
                            now,
                            DateUtils.MINUTE_IN_MILLIS
                        )
                            .toString()
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(5.dp),
                        elevation = CardDefaults.elevatedCardElevation(10.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            val painter = if (post.uid != "") {
                                rememberAsyncImagePainter(imageUri)
                            } else {
                                painterResource(R.drawable.user_icon) // Replace with your actual placeholder image resource
                            }

                            Image(
                                painter = painter,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )

                            Text(
                                text = timeAgo,
                                fontSize = 17.sp,
                                modifier = Modifier.padding(top = 20.dp, start = 6.dp)
                            )

                        }



                        AsyncImage(
                            model = post.postUrl,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.aspectRatio(1f / 1f)
                        )
                        Text(
                            text = post.caption,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(start = 5.dp, top = 10.dp),
                            color = colorResource(
                                id = R.color.black
                            )
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(color = colorResource(id = R.color.lightWhite)),
                            horizontalArrangement = Arrangement.Start
                        ) {


                            var isLiked by remember { mutableStateOf(false) } // Use a boolean for easier toggling

                            Image(
                                painter = painterResource(id = if (isLiked) R.drawable.heart_like else R.drawable.heart),
                                contentDescription = "Favorite",
                                modifier = Modifier
                                    .size(40.dp)
                                    .padding(start = 5.dp, top = 3.dp)
                                    .clickable { isLiked = !isLiked }  // Toggle the state on click
                            )



                            Image(
                                painter = painterResource(id = R.drawable.send),
                                contentDescription = "null",
                                modifier = Modifier
                                    .padding(start = 20.dp, top = 3.dp)
                                    .size(35.dp)
                                    .clickable {
                                        shareImage(context, post.postUrl)
                                    }
                            )

                            Image(
                                painter = painterResource(id = R.drawable.save),
                                contentDescription = "null",
                                modifier = Modifier
                                    .padding(start = 220.dp, top = 3.dp)
                                    .size(40.dp)
                            )

                        }


                    }

                    Spacer(modifier = Modifier.height(30.dp))

                }


            }


        }

    }


}


private fun shareImage(context: Context, imageUrl: String) {
    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, imageUrl)
        type = "text/plain" // Use "text/plain" for sharing a URL
    }

    val shareIntent = Intent.createChooser(sendIntent, null)
    startActivity(context, shareIntent, null)
}