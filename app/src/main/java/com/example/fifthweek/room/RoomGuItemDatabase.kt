package com.ssacproject.fourthweek.room

import android.content.Context
import android.os.AsyncTask
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.fifthweek.room.DataConverter
import com.example.fifthweek.room.GuItem
import com.example.fifthweek.room.MainFoodItem

@Database(entities = [GuItem::class], version = 1, exportSchema = false)
@TypeConverters(DataConverter::class)
abstract class RoomGuItemDatabase : RoomDatabase() {
    abstract val roomGuItemDao : RoomGuItemDao
}