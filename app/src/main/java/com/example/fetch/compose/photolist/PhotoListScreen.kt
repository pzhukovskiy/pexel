package com.example.fetch.compose.photolist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    var query by remember { mutableStateOf("") }

    LaunchedEffect(key1 = Unit, block = {
        viewModel.getPhoto()
    })

    Scaffold { paddingValues ->
        if (viewModel.errorMessage.isBlank()) {
            Column(modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
            ) {
                TextField(
                    value = query,
                    onValueChange = {
                        query = it
                        viewModel.filterPhotosByPhotographer(it)
                    },
                    label = { Text("Search") },
                    textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.onSurface),
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                )
                LazyColumn(content = {
                    items(viewModel.filteredPhotoList) { photo ->
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
        } else {
            Text(text = viewModel.errorMessage)
        }
    }
}