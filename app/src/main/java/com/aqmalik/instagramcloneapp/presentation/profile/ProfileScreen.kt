package com.aqmalik.instagramcloneapp.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.aqmalik.instagramcloneapp.R
import com.aqmalik.instagramcloneapp.common.resource.State
import com.aqmalik.instagramcloneapp.presentation.component.BottomNavigationScaffold
import com.aqmalik.instagramcloneapp.presentation.navgraph.Routes
import com.aqmalik.instagramcloneapp.presentation.util.Const
import com.aqmalik.instagramcloneapp.presentation.viewModel.ICViewmodel

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ICViewmodel
) {




    val state by viewModel.state.collectAsState()

    /*var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf("") }*/




    val firebaseUser = FirebaseAuth.getInstance().currentUser ?: throw Exception("User not logged in")

    val name = firebaseUser.displayName
    val email = firebaseUser.email
    val imageUri = firebaseUser.photoUrl?.toString()


    LaunchedEffect(Unit) {
        viewModel.getUserDetails()
    }


    /*LaunchedEffect(state) {
        if (state is State.Success) {
            val userData = viewModel.userData
            if (userData != null) {
                name = userData.name ?: ""
                email = userData.email
                imageUri = (userData.profileImageUrl ?: "").toString()
            }
        }
    }*/

    BottomNavigationScaffold(navController = navController) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 50.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (name != null) {
                    if (email != null) {
                        if (imageUri != null) {
                            ProfileContent(
                                name = name,
                                email = email,
                                profileImageUrl = imageUri,
                                navController = navController
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                PostReelPager(viewModel)
            }
        }
    }

    when (state) {
        State.Loading -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }

        else -> {}
    }
}


@Composable
fun ProfileContent(
    name: String, email: String, profileImageUrl: String, navController: NavController,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {

        val painter = if (profileImageUrl != "") {
            rememberAsyncImagePainter(profileImageUrl)
        } else {
            painterResource(R.drawable.user_icon) // Replace with your actual placeholder image resource
        }

        val updatedName = if (name == "") {
            "name"
        } else {
            name
        }
        val updatedEmail = if (email == "") {
            "example@gmail.com"
        } else {
            email
        }

        Spacer(modifier = Modifier.width(Const.SmallPadding))

        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = updatedName,
                modifier = Modifier.padding(top = 10.dp, start = 10.dp),
            )
            Text(
                text = updatedEmail,
                modifier = Modifier.padding(10.dp)
            )

            Button(
                onClick = {
                    navController.navigate(Routes.Update.route)
                },
                modifier = Modifier.padding(start = 60.dp),
                colors = ButtonDefaults.buttonColors(Color.Blue)
            ) {
                Text(text = "Update Profile", color = Color.White)
            }
        }
    }
}















