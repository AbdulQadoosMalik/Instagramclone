package com.neatroots.instagramcloneapp.presentation.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.neatroots.instagramcloneapp.R
import com.neatroots.instagramcloneapp.presentation.navgraph.bottomNavigation.mergedList

@Composable
fun BottomNavigationScaffold(
    navController: NavController,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) },
        containerColor = colorResource(id = R.color.white)
    ) { innerPadding ->
        content(innerPadding)
    }
}

@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    NavigationBar(
        containerColor = colorResource(id = R.color.white),
        modifier = Modifier.height(55.dp)
    ) {
        mergedList.forEach { screenWithIcon ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = screenWithIcon.icon),
                        contentDescription = null,
                        modifier = Modifier.size(23.dp)
                    )
                },
                selected = currentRoute == screenWithIcon.route,
                onClick = {
                    if (currentRoute != screenWithIcon.route) {
                        navController.navigate(screenWithIcon.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Gray,
                    unselectedIconColor = colorResource(id = R.color.lightWhite),
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}