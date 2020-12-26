package com.example.eshc.database.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.eshc.R
import com.example.eshc.model.Guards
import com.example.eshc.model.Items


@Dao
interface ItemRoomDao {


 @Query("SELECT * FROM items_table")
 fun getAllItems(): LiveData<List<Items>>

 @Query("SELECT * FROM items_table WHERE order00 = :order00 ORDER BY objectName Asc")
 fun getMainItemList00(order00: String): LiveData<List<Items>>


 @Query("SELECT * FROM items_table WHERE order02 = :order02 ORDER BY objectName Asc")
 fun getMainItemList02(order02: String): LiveData<List<Items>>


 @Query("SELECT * FROM items_table WHERE order04 = :order04 ORDER BY objectName Asc")
 fun getMainItemList04(order04: String): LiveData<List<Items>>


 @Query("SELECT * FROM items_table WHERE order06 = :order06 ORDER BY objectName Asc")
 fun getMainItemList06(order06: String): LiveData<List<Items>>


 @Query("SELECT * FROM items_table WHERE order08 = :order08 ORDER BY objectName Asc")
 fun getMainItemList08(order08: String): LiveData<List<Items>>


 @Query("SELECT * FROM items_table WHERE order15 = :order15 ORDER BY objectName Asc")
 fun getMainItemList15(order15: String): LiveData<List<Items>>


 @Query("SELECT * FROM items_table WHERE order21 = :order21 ORDER BY objectName Asc")
 fun getMainItemList21(order21: String): LiveData<List<Items>>







 @Query("SELECT * FROM guards_table")
 fun getAllGuardsLate(): LiveData<List<Guards>>

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

