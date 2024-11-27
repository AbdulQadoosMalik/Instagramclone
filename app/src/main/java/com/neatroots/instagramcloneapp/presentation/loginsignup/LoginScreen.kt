package com.neatroots.instagramcloneapp.presentation.loginsignup

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.neatroots.instagramcloneapp.R
import com.neatroots.instagramcloneapp.common.resource.NavigationEvent
import com.neatroots.instagramcloneapp.common.resource.State
import com.neatroots.instagramcloneapp.domain.model.UserData
import com.neatroots.instagramcloneapp.presentation.component.CustomButton
import com.neatroots.instagramcloneapp.presentation.component.CustomTextField
import com.neatroots.instagramcloneapp.presentation.navgraph.Routes
import com.neatroots.instagramcloneapp.presentation.util.Const.BottomPadding
import com.neatroots.instagramcloneapp.presentation.util.Const.LargeSpacerHeight
import com.neatroots.instagramcloneapp.presentation.util.Const.MediumFontSize
import com.neatroots.instagramcloneapp.presentation.util.Const.MediumLargeFontSize
import com.neatroots.instagramcloneapp.presentation.util.Const.SmallSpacerHeight
import com.neatroots.instagramcloneapp.presentation.util.Const.TopPadding
import com.neatroots.instagramcloneapp.presentation.viewModel.ICViewmodel
import com.neatroots.instagramcloneapp.ui.theme.AccentColor

@Composable
fun LoginScreen(
    viewModel: ICViewmodel,
    navController: NavHostController
) {
    val state by viewModel.state.collectAsState()

    val context = LocalContext.current
    val navigationEvent by viewModel.navigationEvent.collectAsState(initial = null)

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

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
            Image(
                painter = painterResource(id = R.drawable.style_logo),
                contentDescription = null,
                modifier = Modifier.size(width = 400.dp, height = 60.dp),

                )
        }

        Spacer(modifier = Modifier.height(LargeSpacerHeight))


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

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Forgot password?",
            style = MaterialTheme.typography.bodyMedium,
            fontSize = MediumFontSize,
            color = AccentColor,
            textAlign = TextAlign.End,
            modifier = Modifier
                .padding(end = 16.dp)
                .align(Alignment.End)
                .clickable { }
        )

        Spacer(modifier = Modifier.height(30.dp))

        CustomButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            text = "Log in",
            onClick = {
                val emailResult = viewModel.validateEmail(email)
                val passwordResult = viewModel.validatePassword(password)
                if (emailResult.isValid && passwordResult.isValid) {
                    viewModel.login(
                        userData = UserData(email = email, password = password)
                    )
                }
            }
        )


        Spacer(modifier = Modifier.height(LargeSpacerHeight))

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = BottomPadding)
        ) {
            Text(
                text = "Don't have an account?",
                fontSize = MediumLargeFontSize,
            )
            Spacer(modifier = Modifier.width(SmallSpacerHeight))
            Text(
                text = "SignUp",
                modifier = Modifier.clickable {
                    navController.navigate(Routes.SignUp.route)
                },
                color = colorResource(id = R.color.blue),
                fontSize = MediumLargeFontSize,
            )
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

}