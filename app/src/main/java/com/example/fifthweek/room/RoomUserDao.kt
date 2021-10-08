package com.ssacproject.fourthweek.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RoomUserDao {
    @Query("select * from room_user1")
    fun getAll() : List<RoomUserInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(board: RoomUserInfo)

    @Delete
    fun delete(board: RoomUserInfo)

    @Query("delete from room_user1")
    fun deleteAll()
}