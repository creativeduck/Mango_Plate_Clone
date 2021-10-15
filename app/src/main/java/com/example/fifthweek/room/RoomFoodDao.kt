package com.ssacproject.fourthweek.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.fifthweek.room.MainFoodItem

@Dao
interface RoomFoodDao {
    @Query("select * from room_food_enpyeong2")
    fun getAll() : List<MainFoodItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: MainFoodItem)

    @Delete
    fun delete(item: MainFoodItem)

    @Query("delete from room_food_enpyeong2")
    fun deleteAll()

    @Query("select * from room_food_enpyeong2 where location = :inputTitle ")
    fun getTitleList(inputTitle: String) : List<MainFoodItem>


}