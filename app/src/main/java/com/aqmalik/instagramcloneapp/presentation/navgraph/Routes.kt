package com.aqmalik.instagramcloneapp.presentation.navgraph


sealed class Routes(val route: String) {

    data object SignUp : Routes("signup")

    data object Login : Routes("login")

    data object Home : Routes("home")

    data object Search : Routes("search")

    data object Add : Routes("add")

    data object Reels : Routes("reels")

    data object Profile : Routes("profile")

    data object Update : Routes("update")

    data object CreatePost : Routes("create_post")

    data object CreateReel : Routes("create_reel")

    data object ViewPost : Routes("view_post")

    data object ViewReel : Routes("view_reel")

}




