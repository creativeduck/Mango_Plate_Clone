package com.example.fifthweek.seoul

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.fifthweek.room.MainFoodItem

@Dao
interface RoomSeoulDao {
    @Query("select * from room_seoul_1")
    fun getAll() : List<Row>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: Row)

    @Delete
    fun delete(item: Row)

    @Query("delete from room_seoul_1")
    fun deleteAll()
//
//    @Query("select * from room_food_enpyeong2 where location = :inputTitle ")
//    fun getTitleList(inputTitle: String) : List<MainFoodItem>


}