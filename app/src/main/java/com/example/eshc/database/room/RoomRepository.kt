package com.example.eshc.database.room

import androidx.lifecycle.LiveData
import com.example.eshc.model.Guards
import com.example.eshc.model.Items


class RoomRepository(private val itemRoomDao: ItemRoomDao) {

    suspend fun selectAllItems(): List<Items>{
      return  itemRoomDao.selectAllItems()
    }




    val allItems: LiveData<List<Items>>
        get() = itemRoomDao.getAllItems()

    val mainItemList08: LiveData<List<Items>>
        get() = itemRoomDao.getMainItemList08("true")


    val mainItemList02: LiveData<List<Items>>
        get() = itemRoomDao.getMainItemList02("true")


    val allGuardsLate: LiveData<List<Guards>>
        get() = itemRoomDao.getAllGuardsLate()

    suspend fun insertItem(item: Items) {
        itemRoomDao.insertItem(item)
    }

    suspend fun insertMainItemList(list: MutableList<Items>) {
        itemRoomDao.insertMainItemList(list)
    }

    suspend fun insertGuardLate(guard: Guards) {
        itemRoomDao.insertGuardLate(guard)
    }

    suspend fun deleteItem(item: Items) {
        itemRoomDao.deleteItem(item)
    }

    suspend fun deleteGuard(guard: Guards) {
        itemRoomDao.deleteGuard(guard)
    }

}

