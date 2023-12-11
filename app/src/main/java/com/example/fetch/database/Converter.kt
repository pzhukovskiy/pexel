package com.example.fetch.database

import androidx.room.TypeConverter
import com.example.fetch.data.Source
import com.google.gson.Gson

class Converter {

    @TypeConverter
    fun fromSource(source: Source): String {
        return Gson().toJson(source)
    }

    @TypeConverter
    fun toSource(json: String): Source {
        return Gson().fromJson(json, Source::class.java)
    }
}
