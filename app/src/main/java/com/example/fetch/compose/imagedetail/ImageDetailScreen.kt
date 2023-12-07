package com.example.fetch.compose.imagedetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.fetch.data.Photo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageDetailScreen(
    photo: Photo,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                title = {

                }
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(paddingValues)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize()
                ) {
                    Text(
                        text = "Image information",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Text(text = "Name: ${photo.photographer}")
                    Text(text = "Id: ${photo.id}")
                }
            }
        }
    )
}