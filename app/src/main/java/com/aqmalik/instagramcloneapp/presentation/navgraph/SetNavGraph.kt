package com.aqmalik.instagramcloneapp.presentation.navgraph

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aqmalik.instagramcloneapp.presentation.add.AddScreen
import com.aqmalik.instagramcloneapp.presentation.add.CreateReelScreen
import com.aqmalik.instagramcloneapp.presentation.add.PostScreen
import com.aqmalik.instagramcloneapp.presentation.home.HomeScreen
import com.aqmalik.instagramcloneapp.presentation.loginsignup.LoginScreen
import com.aqmalik.instagramcloneapp.presentation.loginsignup.SignUpScreen
import com.aqmalik.instagramcloneapp.presentation.loginsignup.UpdateScreen
import com.aqmalik.instagramcloneapp.presentation.profile.ProfileScreen
import com.aqmalik.instagramcloneapp.presentation.reels.ReelsScreen
import com.aqmalik.instagramcloneapp.presentation.search.SearchScreen
import com.aqmalik.instagramcloneapp.presentation.viewModel.ICViewmodel


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

