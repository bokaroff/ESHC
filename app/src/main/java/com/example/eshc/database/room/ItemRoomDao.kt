package com.example.eshc.database.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.eshc.model.Guards
import com.example.eshc.model.Items
import com.example.eshc.utilits.*


@Dao
interface ItemRoomDao {

 @Query(getAllItems)
 fun getAllItems(): LiveData<List<Items>>

 @Query(getAllChangedItems)
 fun getAllChangedItems(): LiveData<List<Items>>

 @Query("SELECT * FROM guards_table")
 fun getAllGuardsLate(): LiveData<List<Guards>>


 @Query("SELECT * FROM items_table WHERE order00 = 'true' AND state = 'main' ORDER BY objectName Asc")
 fun getMainItemList00(): LiveData<List<Items>>


 @Query("SELECT * FROM items_table WHERE order02 = 'true' AND state = 'main' ORDER BY objectName Asc")
 fun getMainItemList02(): LiveData<List<Items>>


 @Query("SELECT * FROM items_table WHERE order04 = 'true' AND state = 'main' ORDER BY objectName Asc")
 fun getMainItemList04(): LiveData<List<Items>>


 @Query("SELECT * FROM items_table WHERE order06 = 'true' AND state = 'main' ORDER BY objectName Asc")
 fun getMainItemList06(): LiveData<List<Items>>


 @Query("SELECT * FROM items_table WHERE order08 = 'true' AND state = 'main' ORDER BY objectName Asc")
 fun getMainItemList08(): LiveData<List<Items>>


 @Query("SELECT * FROM items_table WHERE order15 = 'true' AND state = 'main' ORDER BY objectName Asc")
 fun getMainItemList15(): LiveData<List<Items>>


 @Query("SELECT * FROM items_table WHERE order21 = 'true' AND state = 'main' ORDER BY objectName Asc")
 fun getMainItemList21(): LiveData<List<Items>>


 @Query("SELECT * FROM items_table")
 suspend fun selectAllItems(): List<Items>

 @Insert(onConflict = OnConflictStrategy.IGNORE)
 suspend fun insertItem(item: Items)

 @Insert(onConflict = OnConflictStrategy.IGNORE)
 suspend fun insertMainItemList(list: MutableList<Items>)

 @Insert(onConflict = OnConflictStrategy.IGNORE)
 suspend fun insertGuardLate(guard: Guards)

 @Delete
 suspend fun deleteItem(item: Items)

 @Delete
 suspend fun deleteGuard(guard: Guards)

}

