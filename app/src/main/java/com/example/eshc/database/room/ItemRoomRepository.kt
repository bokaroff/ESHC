package com.example.eshc.database.room

import com.example.eshc.model.Items


class ItemRoomRepository(private val itemRoomDao: ItemRoomDao) {

        val allItems_Entity
                get() = itemRoomDao.getAllItems()

        suspend fun insert(item: Items){
                itemRoomDao.insert(item)
        }

        suspend fun delete(item: Items){
        itemRoomDao.delete(item)
    }

}

