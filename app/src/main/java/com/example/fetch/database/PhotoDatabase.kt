package com.example.fetch.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.fetch.data.Photo
import com.example.fetch.database.dao.PhotoDao

@TypeConverters(Converter::class)
@Database(
    entities = [Photo::class],
    version = 1
)
abstract class PhotoDatabase: RoomDatabase() {
    abstract val dao: PhotoDao
}