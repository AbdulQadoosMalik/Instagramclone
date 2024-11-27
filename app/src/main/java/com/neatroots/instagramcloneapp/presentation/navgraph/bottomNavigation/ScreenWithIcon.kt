package com.neatroots.instagramcloneapp.presentation.navgraph.bottomNavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.neatroots.instagramcloneapp.R
import com.neatroots.instagramcloneapp.presentation.navgraph.Routes

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


