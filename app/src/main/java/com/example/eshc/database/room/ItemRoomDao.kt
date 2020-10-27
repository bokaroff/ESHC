package com.example.eshc.database.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.eshc.model.Items


@Dao
interface ItemRoomDao {

   @Query("SELECT * FROM items_table")
    fun getAllItems(): LiveData<List<Items>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Items)

    @Delete
    suspend fun delete(item: Items)

}

