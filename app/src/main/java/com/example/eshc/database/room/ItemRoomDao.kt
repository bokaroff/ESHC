package com.example.eshc.database.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

 @Query(getAllGuardsLate)
 fun getAllGuardsLate(): LiveData<List<Guards>>


 @Query(getMainItemList00)
 fun getMainItemList00(): LiveData<List<Items>>


 @Query(getMainItemList02)
 fun getMainItemList02(): LiveData<List<Items>>


 @Query(getMainItemList04)
 fun getMainItemList04(): LiveData<List<Items>>


 @Query(getMainItemList06)
 fun getMainItemList06(): LiveData<List<Items>>


 @Query(getMainItemList08)
 fun getMainItemList08(): LiveData<List<Items>>


 @Query(getMainItemList15)
 fun getMainItemList15(): LiveData<List<Items>>


 @Query(getMainItemList21)
 fun getMainItemList21(): LiveData<List<Items>>


 @Query(getAllMainItems)
 suspend fun getMainItemList(): List<Items>

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

