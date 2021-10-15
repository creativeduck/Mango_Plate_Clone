package com.ssacproject.fourthweek.room

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [RoomUserInfo::class], version = 1, exportSchema = false)
abstract class RoomUserDatabase : RoomDatabase() {
    abstract val roomUserDao : RoomUserDao
}