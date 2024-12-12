package com.aqmalik.instagramcloneapp.presentation.loginsignup

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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.aqmalik.instagramcloneapp.R
import com.aqmalik.instagramcloneapp.common.resource.NavigationEvent
import com.aqmalik.instagramcloneapp.common.resource.State
import com.aqmalik.instagramcloneapp.presentation.component.CustomButton
import com.aqmalik.instagramcloneapp.presentation.component.CustomTextField
import com.aqmalik.instagramcloneapp.presentation.loginsignup.component.RoundedImageWithIcon
import com.aqmalik.instagramcloneapp.presentation.navgraph.Routes
import com.aqmalik.instagramcloneapp.presentation.util.Const.LargeSpacerHeight
import com.aqmalik.instagramcloneapp.presentation.util.Const.MediumLargeSpacerHeight
import com.aqmalik.instagramcloneapp.presentation.util.Const.MediumSpacerHeight
import com.aqmalik.instagramcloneapp.presentation.util.Const.TopPadding
import com.aqmalik.instagramcloneapp.presentation.viewModel.ICViewmodel


@Composable
fun UpdateScreen(
    viewModel: ICViewmodel, navController: NavHostController
) {

    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val navigationEvent by viewModel.navigationEvent.collectAsState(initial = null)


    var profileImage by remember { mutableStateOf<Uri?>(null) }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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
            RoundedImageWithIcon { uri ->
                profileImage = uri
            }

        }

        Spacer(modifier = Modifier.height(MediumSpacerHeight))

        CustomTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            hint = "Name",
            value = name,
            onValueChange = { name = it },
            keyboardType = KeyboardType.Text,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
        )


        Spacer(modifier = Modifier.height(MediumSpacerHeight))


        CustomTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            hint = "Email",
            value = email,
            onValueChange = { email = it },
            keyboardType = KeyboardType.Email,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
        )



        Spacer(modifier = Modifier.height(12.dp))

        CustomTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            hint = "Password",
            value = password,
            onValueChange = { password = it },
            visualTransformation = PasswordVisualTransformation(),
            keyboardType = KeyboardType.Password,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            )
        )


        Spacer(modifier = Modifier.height(MediumLargeSpacerHeight))


        CustomButton(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
            text = "Update",
            onClick = {
                val nameResult = viewModel.validateName(name)
                val emailResult = viewModel.validateEmail(email)
                val passwordResult = viewModel.validatePassword(password)
                val imageResult = viewModel.validateImage(profileImage)

                if (nameResult.isValid && emailResult.isValid && passwordResult.isValid && imageResult.isValid) {
                    viewModel.update(
                        name = name,
                        email = email,
                        password = password,
                        profileImage = profileImage
                    )
                } else {
                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                }
            }
        )


    }

    Spacer(modifier = Modifier.height(LargeSpacerHeight))

    // Optionally, handle state UI
    when (state) {
        State.Loading -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }

        else -> {}
    }


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

}

