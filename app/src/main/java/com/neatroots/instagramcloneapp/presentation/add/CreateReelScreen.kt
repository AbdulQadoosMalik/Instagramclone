package com.neatroots.instagramcloneapp.presentation.add

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.neatroots.instagramcloneapp.R
import com.neatroots.instagramcloneapp.common.resource.NavigationEvent
import com.neatroots.instagramcloneapp.common.resource.State
import com.neatroots.instagramcloneapp.presentation.component.CustomButton
import com.neatroots.instagramcloneapp.presentation.component.CustomTextField
import com.neatroots.instagramcloneapp.presentation.viewModel.ICViewmodel

@Composable
fun CreateReelScreen(navController: NavController, viewModel: ICViewmodel) {
    val context = LocalContext.current
    var videoUri by remember { mutableStateOf<Uri?>(null) }
    var caption by remember { mutableStateOf("") }
    val state by viewModel.state.collectAsState()

    val videoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            videoUri = uri
        }
    )


    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                is NavigationEvent.NavigateToRoute -> {
                    navController.navigate(event.route)
                }

                is NavigationEvent.ShowError -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp)
            .background(color = colorResource(id = R.color.white))
    ) {

        Text(
            text = "New Reel",
            fontSize = 25.sp,
            modifier = Modifier.padding(top = 10.dp, start = 20.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .fillMaxHeight(.1f)
                .fillMaxWidth(.9f)
                .padding(start = 25.dp)
                .clip(shape = RoundedCornerShape(5.dp))
                .background(colorResource(id = R.color.lightWhite))
                .clickable { videoPickerLauncher.launch("video/*") }
        ) {
            if (videoUri != null) {
                Text(
                    text = "Video Selected",
                    modifier = Modifier
                        .padding(16.dp),
                    color = Color.White
                )
            } else {
                Text(
                    text = "Select a Video",
                    modifier = Modifier
                        .padding(16.dp),
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp),
            hint = "Enter caption",
            value = caption,
            onValueChange = { caption = it },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            )
        )


        Spacer(modifier = Modifier.height(16.dp))


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
        ) {
            Image(
                painterResource(id = R.drawable.video),
                contentDescription = null,
                modifier = Modifier
                    .height(30.dp)
                    .padding(start = 30.dp)
            )
            Text(text = "Share to Reel", fontSize = 19.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 20.dp))
        }
        Text(text = "Your Post May be appear in Posts and can be seen on the post tab under your profile", modifier = Modifier.padding(20.dp))

        Spacer(modifier = Modifier.height(16.dp))

        CustomButton(
            modifier = Modifier
                .fillMaxWidth(.9f)
                .padding(start = 30.dp),
            text = "Post Reel",
            onClick = {
                if (videoUri != null && caption.isNotBlank()) {
                    viewModel.createReel(videoUri.toString(), caption)
                } else {
                    Toast.makeText(
                        context,
                        "Please select a video and enter a caption",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )


    }


    when (state) {
        is State.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is State.Progress -> {
            val progress = (state as State.Progress).progress / 100f
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                LinearProgressIndicator(
                    progress = { progress },
                    color = Color.White,
                    modifier = Modifier.height(10.dp)
                )
                Text(text = "${(progress * 100).toInt()}%")

            }
        }

        is State.Error -> {
            val errorMessage = (state as State.Error).message
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }

        else -> {}
    }


}
