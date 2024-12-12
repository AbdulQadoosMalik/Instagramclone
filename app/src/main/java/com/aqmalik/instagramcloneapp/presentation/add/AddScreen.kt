package com.aqmalik.instagramcloneapp.presentation.add

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.aqmalik.instagramcloneapp.presentation.component.BottomNavigationScaffold
import com.aqmalik.instagramcloneapp.presentation.component.CustomButton
import com.aqmalik.instagramcloneapp.presentation.navgraph.Routes
import com.aqmalik.instagramcloneapp.presentation.util.Const.LargeFontSize
import com.aqmalik.instagramcloneapp.presentation.util.Const.LargeSpacerHeight


@Composable
fun AddScreen(modifier: Modifier = Modifier, navController: NavHostController, viewModel: Any) {
    BottomNavigationScaffold(navController = navController) { innerPadding ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            Text(
                "Create Post And Reel",
                modifier = Modifier
                    .padding(start = 40.dp, top = 40.dp),
                fontSize = LargeFontSize,
            )


            Spacer(modifier = Modifier.height(LargeSpacerHeight))

            CustomButton(
                text = "Create Post",
                onClick = {
                    navController.navigate(Routes.CreatePost.route)
                },
                modifier = Modifier.padding(start = 110.dp, top = 250.dp)

                )

            CustomButton(
                text = "Create Reel",
                onClick = {
                    navController.navigate(Routes.CreateReel.route)
                },
                modifier = Modifier.padding(start = 110.dp, top = 60.dp)

            )


        }
    }
}


