package com.example.fetch.compose.photodetail

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.fetch.data.Photo
import com.example.fetch.database.event.PhotoEvent

@Composable
fun PhotoDetailScreen(
    photo: Photo,
    onBackClick: () -> Unit,
    onEvent: (PhotoEvent) -> Unit
) {

    val context = LocalContext.current

    LaunchedEffect(key1 = Unit, block = {
        onEvent(PhotoEvent.SetId(photo.id))
        onEvent(PhotoEvent.SetWidth(photo.width))
        onEvent(PhotoEvent.SetHeight(photo.height))
        onEvent(PhotoEvent.SetUrl(photo.url))
        onEvent(PhotoEvent.SetPhotographer(photo.photographer))
        onEvent(PhotoEvent.SetPhotographerId(photo.photographerId))
        onEvent(PhotoEvent.SetLiked(photo.liked))
        onEvent(PhotoEvent.SetAlt(photo.alt))
    })

    Scaffold(
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(paddingValues)
            ) {
                AsyncImage(
                    model = photo.src.landscape,
                    contentDescription = photo.photographer,
                    modifier = Modifier
                        .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                )
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(Color.White, shape = CircleShape),
                        contentAlignment = Alignment.Center
                    )  {
                        IconButton(
                            onClick = { onBackClick() },
                            modifier = Modifier
                                .size(30.dp)
                        ) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.Black
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .padding(top = 160.dp)
                    ) {
                        Column {
                            Text(
                                text = "Image information",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                            Text(text = "Name: ${photo.photographer}")
                            Text(text = "Id: ${photo.id}")
                            Button(onClick = {
                                try {
                                    onEvent(PhotoEvent.SavePhoto)
                                    Toast.makeText(context, "Photo saved successful", Toast.LENGTH_SHORT).show()
                                } catch (e: Exception) {
                                    Toast.makeText(context, "Error downloaded photo", Toast.LENGTH_SHORT).show()
                                    throw e
                                }
                            }) {
                                Text(text = "Save photo")
                            }
                        }
                    }
                }
            }
        }
    )
}