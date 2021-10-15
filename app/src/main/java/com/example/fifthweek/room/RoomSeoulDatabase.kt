package com.ssacproject.fourthweek.room

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.fifthweek.room.MainFoodItem
import com.example.fifthweek.seoul.RoomSeoulDao
import com.example.fifthweek.seoul.Row

@Database(entities = [Row::class], version = 1, exportSchema = false)
abstract class RoomSeoulDatabase : RoomDatabase() {
    abstract val roomSeoulDao : RoomSeoulDao
}