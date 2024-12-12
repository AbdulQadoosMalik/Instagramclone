package com.aqmalik.instagramcloneapp.presentation.add

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.aqmalik.instagramcloneapp.R
import com.aqmalik.instagramcloneapp.common.resource.NavigationEvent
import com.aqmalik.instagramcloneapp.common.resource.State
import com.aqmalik.instagramcloneapp.presentation.component.CustomButton
import com.aqmalik.instagramcloneapp.presentation.component.CustomTextField
import com.aqmalik.instagramcloneapp.presentation.component.RectangleImageWithIcon
import com.aqmalik.instagramcloneapp.presentation.navgraph.Routes
import com.aqmalik.instagramcloneapp.presentation.util.Const.LargeSpacerHeight
import com.aqmalik.instagramcloneapp.presentation.util.Const.MediumLargeSpacerHeight
import com.aqmalik.instagramcloneapp.presentation.util.Const.TopPadding
import com.aqmalik.instagramcloneapp.presentation.viewModel.ICViewmodel


@Composable
fun PostScreen(viewModel: ICViewmodel, navController: NavController) {

    val state by viewModel.state.collectAsState()
    var postImage by rememberSaveable { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val navigationEvent by viewModel.navigationEvent.collectAsState(initial = null)

    var caption by rememberSaveable { mutableStateOf("") }
    val uid by rememberSaveable { mutableStateOf(Firebase.auth.currentUser!!.uid) }
    val time by rememberSaveable { mutableStateOf(System.currentTimeMillis().toString()) }

    Column(
        modifier = Modifier
            .background(color = colorResource(id = R.color.white))
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {


        Column(
            modifier = Modifier.padding(top = TopPadding),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RectangleImageWithIcon { uri ->
                postImage = uri
            }

        }

        Spacer(modifier = Modifier.height(LargeSpacerHeight))

        CustomTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(horizontal = 30.dp),
            hint = "Caption",
            value = caption,
            onValueChange = { caption = it },
            keyboardType = KeyboardType.Text,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
        )

        Spacer(modifier = Modifier.height(MediumLargeSpacerHeight))

        CustomButton(text = "Add Post", onClick = {

            viewModel.createPost(
                postUrl = postImage.toString(),
                caption = caption,
                uid = uid,
                time = time
            )
        })

        LaunchedEffect(navigationEvent) {
            when (navigationEvent) {
                is NavigationEvent.NavigateToRoute -> {
                    navController.navigate(Routes.Home.route)
                }

                is NavigationEvent.ShowError -> {
                    Toast.makeText(
                        context,
                        (navigationEvent as NavigationEvent.ShowError).message,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {}
            }
        }

        // Optionally, handle state UI
        when (state) {
            State.Loading ->
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }

            State.Success -> {

            }

            else -> {}
        }
    }
}
