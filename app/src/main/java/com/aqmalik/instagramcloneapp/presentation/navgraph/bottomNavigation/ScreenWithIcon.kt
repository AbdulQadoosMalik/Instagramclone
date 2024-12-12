package com.aqmalik.instagramcloneapp.presentation.navgraph.bottomNavigation

import com.aqmalik.instagramcloneapp.R
import com.aqmalik.instagramcloneapp.presentation.navgraph.Routes

data class ScreenWithIcon(
    val route: String,
    val icon: Int
)


val mergedList = listOf(
    ScreenWithIcon(route = Routes.Home.route, icon = R.drawable.home),
    ScreenWithIcon(route = Routes.Search.route, icon = R.drawable.search),
    ScreenWithIcon(route = Routes.Add.route, icon = R.drawable.plus),
    ScreenWithIcon(route = Routes.Reels.route, icon = R.drawable.video),
    ScreenWithIcon(route = Routes.Profile.route, icon = R.drawable.user_profile)
)


