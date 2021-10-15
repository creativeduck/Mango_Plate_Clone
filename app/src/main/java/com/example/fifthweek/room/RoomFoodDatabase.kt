package com.ssacproject.fourthweek.room

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.fifthweek.room.MainFoodItem

@Database(entities = [MainFoodItem::class], version = 1, exportSchema = false)
abstract class RoomFoodDatabase : RoomDatabase() {
    abstract val roomFoodDao : RoomFoodDao
}