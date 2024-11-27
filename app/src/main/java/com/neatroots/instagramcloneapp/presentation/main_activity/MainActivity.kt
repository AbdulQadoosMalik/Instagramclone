package com.neatroots.instagramcloneapp.presentation.main_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.neatroots.instagramcloneapp.presentation.add.CreateReelScreen
import com.neatroots.instagramcloneapp.presentation.loginsignup.SignUpScreen
import com.neatroots.instagramcloneapp.presentation.loginsignup.UpdateScreen
import com.neatroots.instagramcloneapp.presentation.viewModel.ICViewmodel
import com.neatroots.instagramcloneapp.presentation.navgraph.NavGraph
import com.neatroots.instagramcloneapp.presentation.profile.ProfileScreen
import com.neatroots.instagramcloneapp.ui.theme.InstagramCloneAppTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val viewModel: ICViewmodel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            InstagramCloneAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    innerPadding
                    val navController = rememberNavController()
                    NavGraph(viewModel = viewModel)
                   /* ProfileScreen(navController = navController, viewModel = viewModel)*/
                }
            }
        }
    }
}
