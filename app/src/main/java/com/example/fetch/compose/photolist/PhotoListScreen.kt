package com.example.fetch.compose.photolist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.fetch.data.Photo
import com.example.fetch.viewmodels.PhotoViewModel

@Composable
fun PhotoListScreen(
    viewModel: PhotoViewModel,
    onImageClick: (Photo) -> Unit,
) {

    LaunchedEffect(key1 = Unit, block = {
        viewModel.getPhoto()
    })

    Scaffold { paddingValues ->
        if (viewModel.errorMessage.isBlank()) {
            Column(modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                LazyColumn(content = {
                    items(viewModel.photoList) { photo ->
                        AsyncImage(
                            model = photo.src.original,
                            contentDescription = photo.photographer,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(16.dp))
                                .clickable {
                                    onImageClick(photo)
                                }
                                .padding(top = 2.5.dp, bottom = 2.5.dp)
                        )
                    }
                })
            }
        }
        else {
            Text(text = viewModel.errorMessage)
        }
    }
}