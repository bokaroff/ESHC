package com.example.eshc.database.room

import androidx.lifecycle.LiveData
import com.example.eshc.model.Guards
import com.example.eshc.model.Items
import java.lang.reflect.Array.get


class RoomRepository(private val itemRoomDao: ItemRoomDao) {

    val allItems: LiveData<List<Items>>
        get() = itemRoomDao.getAllItems()

    val allChangedItems: LiveData<List<Items>>
        get() = itemRoomDao.getAllChangedItems()

    val allGuardsLate: LiveData<List<Guards>>
        get() = itemRoomDao.getAllGuardsLate()

    val mainItemList00: LiveData<List<Items>>
        get() = itemRoomDao.getMainItemList00()

    val mainItemList02: LiveData<List<Items>>
        get() = itemRoomDao.getMainItemList02()

    val mainItemList04: LiveData<List<Items>>
        get() = itemRoomDao.getMainItemList04()

    val mainItemList06: LiveData<List<Items>>
        get() = itemRoomDao.getMainItemList06()

    val mainItemList08: LiveData<List<Items>>
        get() = itemRoomDao.getMainItemList08()

    val mainItemList15: LiveData<List<Items>>
        get() = itemRoomDao.getMainItemList15()

    val mainItemList21: LiveData<List<Items>>
        get() = itemRoomDao.getMainItemList21()


    suspend fun selectAllItems(): List<Items> {
        return itemRoomDao.selectAllItems()
    }


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

