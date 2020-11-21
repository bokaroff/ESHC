package com.example.eshc.database.room

import androidx.lifecycle.LiveData
import com.example.eshc.model.Guards
import com.example.eshc.model.Items


class RoomRepository(private val itemRoomDao: ItemRoomDao) {

       val allItems: LiveData<List<Items>>
        get() = itemRoomDao.getAllItems()

       val allGuardsLate: LiveData<List<Guards>>
        get() = itemRoomDao.getAllGuardsLate()

    suspend fun insertItem(item: Items) {
        itemRoomDao.insertItem(item)
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

