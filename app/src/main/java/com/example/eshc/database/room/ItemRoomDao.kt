package com.example.eshc.database.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.eshc.model.Guards
import com.example.eshc.model.Items
import com.example.eshc.utilits.*


@Dao
interface ItemRoomDao {

 @Query(getAllMainItems)
 fun getAllItems(): LiveData<List<Items>>

 @Query(getAllChangedItems)
 fun getAllChangedItems(): LiveData<List<Items>>

 @Query(singleChangedItem)
 suspend fun singleChangedItem(name: String): List<Items>

 @Query(getAllChangedItemsWhereTimeBetween)
 suspend fun getAllChangedItemsWhereTimeBetween(timeStart: Long, timeEnd: Long): List<Items>

 @Query(getAllGuardsLate)
 fun getAllGuardsLate(): LiveData<List<Guards>>


 @Query(getAllMainItems)
 suspend fun getMainItemList(): List<Items>


 @Query(getMainItemList00)
 suspend fun getMainItemList00(): List<Items>


 @Query(getMainItemList02)
 suspend fun getMainItemList02(): List<Items>


 @Query(getMainItemList04)
 suspend fun getMainItemList04(): List<Items>


 @Query(getMainItemList06)
 suspend fun getMainItemList06(): List<Items>


 @Query(getMainItemList08)
 suspend fun getMainItemList08(): List<Items>


 @Query(getMainItemList15)
 suspend fun getMainItemList15(): List<Items>


 @Query(getMainItemList21)
 suspend fun getMainItemList21(): List<Items>


 @Query(getAllMainGuards)
 suspend fun getMainGuardList(): List<Guards>

 @Insert(onConflict = OnConflictStrategy.REPLACE)
 suspend fun insertItem(item: Items)

 @Insert(onConflict = OnConflictStrategy.REPLACE)
 suspend fun insertGuard(guard: Guards)


 @Insert(onConflict = OnConflictStrategy.REPLACE)
 suspend fun insertItemList(list: MutableList<Items>)

 @Insert(onConflict = OnConflictStrategy.REPLACE)
 suspend fun insertGuardList(list: MutableList<Guards>)



 @Query("DELETE FROM items_table WHERE item_id =:item_id and state = 'main' ")
 suspend fun deleteMainItem(item_id: String)

 @Query("DELETE FROM guards_table WHERE guardFire_id =:guardFire_id and state = 'main' ")
 suspend fun deleteMainGuard(guardFire_id: String)

// @Query("")
 //suspend fun updateMainItem()

 @Update
 suspend fun updateMainItem(item: Items)

 @Delete
 suspend fun deleteGuard(guard: Guards)

}

