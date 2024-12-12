package com.aqmalik.instagramcloneapp.presentation.profile

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.sp
import com.aqmalik.instagramcloneapp.R
import com.aqmalik.instagramcloneapp.presentation.viewModel.ICViewmodel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PostReelPager(viewModel: ICViewmodel) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = {2})
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Tab Row to display "Post" and "Reel" tabs
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = colorResource(id = R.color.white),
        ) {
            listOf("Post", "Reel").forEachIndexed { index, title ->
                Tab(
                    text = {
                        Text(
                            title,
                            color = colorResource(id = R.color.black),
                            fontSize = 17.sp
                        )
                    },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            }
        }

        // Pager to show content of "Post" and "Reel"
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            when (page) {
                0 -> ViewPostScreen(viewModel)
                1 -> ViewReelScreen(viewModel)
            }
        }
    }
}











