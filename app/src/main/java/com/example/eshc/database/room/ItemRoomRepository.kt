package com.example.eshc.database.room

import com.example.eshc.model.Guards
import com.example.eshc.model.Items


class ItemRoomRepository(private val itemRoomDao: ItemRoomDao) {

        // val allItems_Entity
        //       get() = itemRoomDao.getAllItems()

        suspend fun getAllItems(): List<Items> {
                return itemRoomDao.getAllItems()
        }

        suspend fun getAllGuardsLate(): MutableList<Guards> {
                return itemRoomDao.getAllGuardsLate()
        }

        suspend fun insertItem(item: Items) {
                itemRoomDao.insertItem(item)
        }

        suspend fun insertGuardLate(guard: Guards) {
                itemRoomDao.insertGuardLate(guard)
        }

        suspend fun delete(item: Items) {
                itemRoomDao.delete(item)
        }

}

