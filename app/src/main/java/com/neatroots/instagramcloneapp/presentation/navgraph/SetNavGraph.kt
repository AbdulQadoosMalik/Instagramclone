package com.neatroots.instagramcloneapp.presentation.navgraph

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.neatroots.instagramcloneapp.presentation.add.AddScreen
import com.neatroots.instagramcloneapp.presentation.add.CreateReelScreen
import com.neatroots.instagramcloneapp.presentation.add.PostScreen
import com.neatroots.instagramcloneapp.presentation.home.HomeScreen
import com.neatroots.instagramcloneapp.presentation.loginsignup.LoginScreen
import com.neatroots.instagramcloneapp.presentation.loginsignup.SignUpScreen
import com.neatroots.instagramcloneapp.presentation.loginsignup.UpdateScreen
import com.neatroots.instagramcloneapp.presentation.profile.ProfileScreen
import com.neatroots.instagramcloneapp.presentation.reels.ReelsScreen
import com.neatroots.instagramcloneapp.presentation.search.SearchScreen
import com.neatroots.instagramcloneapp.presentation.viewModel.ICViewmodel


@Composable
fun NavGraph(viewModel: ICViewmodel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.SignUp.route) {

        composable(Routes.SignUp.route) {
            SignUpScreen(
                navController = navController,
                viewModel = viewModel,
            )

        }

        composable(Routes.Login.route) {
            LoginScreen(
                viewModel = viewModel,
                navController = navController,
            )
        }


        composable(Routes.Home.route) {
            HomeScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        composable(Routes.Search.route) {
            SearchScreen(
                viewModel = viewModel,
                navController = navController,
            )

        }

        composable(Routes.Add.route) {
            AddScreen(
                navController = navController,
                viewModel = viewModel,
            )
        }

        composable(Routes.Reels.route) {
            ReelsScreen(
                navController = navController,
                viewModel = viewModel,
            )
        }

        composable(Routes.Profile.route) {
            ProfileScreen(
                navController = navController,
                viewModel = viewModel,
            )
        }

        composable(Routes.Update.route) {
            UpdateScreen(
                navController = navController,
                viewModel = viewModel,
            )

        }

        composable(Routes.CreatePost.route) {
            PostScreen(
                navController = navController,
                viewModel = viewModel,
            )
        }

        composable(Routes.CreateReel.route) {
            CreateReelScreen(
                navController = navController,
                viewModel = viewModel,
            )
        }


        composable(Routes.ViewPost.route) {
            /*ViewPost(
                navController = navController,
                viewModel = viewModel,
            )*/
        }


        composable(Routes.ViewReel.route) {
            /*ViewReel(
                navController = navController,
                viewModel = viewModel,
            )*/
        }




    }
}

