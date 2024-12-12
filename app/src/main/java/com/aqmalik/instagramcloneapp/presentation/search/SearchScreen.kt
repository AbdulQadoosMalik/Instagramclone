package com.aqmalik.instagramcloneapp.presentation.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.aqmalik.instagramcloneapp.R
import com.aqmalik.instagramcloneapp.presentation.component.BottomNavigationScaffold
import com.aqmalik.instagramcloneapp.presentation.viewModel.ICViewmodel

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: ICViewmodel
) {

    val searchResults by viewModel.searchResult.collectAsState()

    BottomNavigationScaffold(navController = navController) { innerPadding ->
        // Your screen content goes here, for example:

        var searchName by rememberSaveable { mutableStateOf("") }


        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            OutlinedTextField(
                value = searchName,
                onValueChange = { searchName = it },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            viewModel.search(searchName)
                        }
                    )

                },
                modifier = Modifier
                    .padding(start = 15.dp)
                    .fillMaxWidth(.95f),
                placeholder = {
                    Text(text = "search")
                },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = colorResource(id = R.color.lightWhite)
                )

            )



            LazyColumn {
                items(searchResults) { user ->
                    user.name?.let {
                        if (user.name == searchName) {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                            ) {
                                val painter = user.profileImageUrl

                                AsyncImage(
                                    model = painter,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Crop
                                )

                                Text(
                                    text = it, // Display the user's name
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    fontSize = 18.sp
                                )

                            }


                        }


                    }
                }
            }
        }


    }
}













