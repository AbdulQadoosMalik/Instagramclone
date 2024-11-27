package com.neatroots.instagramcloneapp.presentation.add

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.neatroots.instagramcloneapp.R
import com.neatroots.instagramcloneapp.presentation.component.BottomNavigationBar
import com.neatroots.instagramcloneapp.presentation.component.BottomNavigationScaffold
import com.neatroots.instagramcloneapp.presentation.component.CustomButton
import com.neatroots.instagramcloneapp.presentation.navgraph.Routes
import com.neatroots.instagramcloneapp.presentation.navgraph.bottomNavigation.mergedList
import com.neatroots.instagramcloneapp.presentation.util.Const.LargeFontSize
import com.neatroots.instagramcloneapp.presentation.util.Const.LargeSpacerHeight
import com.neatroots.instagramcloneapp.presentation.util.Const.MediumFontSize
import com.neatroots.instagramcloneapp.presentation.util.Const.MediumLargeSpacerHeight


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


