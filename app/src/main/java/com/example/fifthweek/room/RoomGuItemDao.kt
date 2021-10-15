package com.ssacproject.fourthweek.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.fifthweek.room.GuItem
import com.example.fifthweek.room.MainFoodItem

@Dao
interface RoomGuItemDao {
    @Query("select * from room_food_gu")
    fun getAll() : List<GuItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: GuItem)

    @Delete
    fun delete(item: GuItem)

    @Query("delete from room_food_gu")
    fun deleteAll()

    @Query("select * from room_food_gu where gu = :inputTitle ")
    fun getTitleList(inputTitle: String) : List<GuItem>

    @Query("select * from room_food_gu where name = :inputTitle")
    fun getItem(inputTitle: String) : GuItem


}