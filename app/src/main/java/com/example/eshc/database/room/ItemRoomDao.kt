package com.example.eshc.database.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.eshc.model.Guards
import com.example.eshc.model.Items


@Dao
interface ItemRoomDao {

 @Query("SELECT * FROM items_table")
 fun getAllItems(): LiveData<List<Items>>

 @Query("SELECT * FROM guards_table")
 fun getAllGuardsLate(): LiveData<List<Guards>>

 @Insert(onConflict = OnConflictStrategy.IGNORE)
 suspend fun insertItem(item: Items)

 @Insert(onConflict = OnConflictStrategy.IGNORE)
 suspend fun insertGuardLate(guard: Guards)


 @Delete
 suspend fun deleteItem(item: Items)

 @Delete
 suspend fun deleteGuard(guard: Guards)

}

