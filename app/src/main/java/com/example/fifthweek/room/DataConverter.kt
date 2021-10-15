package com.example.fifthweek.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataConverter {
    @TypeConverter
    fun fromArrayListToString(value: ArrayList<String>) : String {
        val gson = Gson()
        val type = object : TypeToken<ArrayList<String>>() {}.type
        return gson.toJson(value, type)
    }
    @TypeConverter
    fun fromStringToArrayList(value: String) : ArrayList<String> {
        val gson = Gson()
        val type = object : TypeToken<ArrayList<String>>() {}.type
        return gson.fromJson(value, type)
    }

}