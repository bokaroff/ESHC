package com.example.eshc.database.room

import androidx.lifecycle.LiveData
import com.example.eshc.model.Guards
import com.example.eshc.model.Items


class RoomRepository(private val itemRoomDao: ItemRoomDao) {

    val allChangedItems: LiveData<List<Items>>
        get() = itemRoomDao.getAllChangedItems()

    val allGuardsLate: LiveData<List<Guards>>
        get() = itemRoomDao.getAllGuardsLate()

/*
    val mainItemList00: LiveData<List<Items>>
        get() = itemRoomDao.getMainItemList00()

    val mainItemList02: LiveData<List<Items>>
        get() = itemRoomDao.getMainItemList02()

    val mainItemList04: LiveData<List<Items>>
        get() = itemRoomDao.getMainItemList04()

    val mainItemList06: LiveData<List<Items>>
        get() = itemRoomDao.getMainItemList06()

    val mainItemList08: List<Items>
       get() = itemRoomDao.getMainItemList08()



    val mainItemList15: LiveData<List<Items>>
        get() = itemRoomDao.getMainItemList15()

    val mainItemList21: LiveData<List<Items>>
        get() = itemRoomDao.getMainItemList21()


 */

    suspend fun getMainItemList(): List<Items> {
        return itemRoomDao.getMainItemList()
    }

    suspend fun getMainItemList08(): List<Items> {
        return itemRoomDao.getMainItemList08()
    }

    suspend fun getMainItemList15(): List<Items> {
        return itemRoomDao.getMainItemList15()
    }

    suspend fun getMainItemList21(): List<Items> {
        return itemRoomDao.getMainItemList21()
    }

    suspend fun getMainItemList00(): List<Items> {
        return itemRoomDao.getMainItemList00()
    }

    suspend fun getMainItemList02(): List<Items> {
        return itemRoomDao.getMainItemList02()
    }

    suspend fun getMainItemList04(): List<Items> {
        return itemRoomDao.getMainItemList04()
    }

    suspend fun getMainItemList06(): List<Items> {
        return itemRoomDao.getMainItemList06()
    }

    suspend fun getAllChangedItemsWhereTimeBetween(timeStart: Long, timeEnd: Long): List<Items> {
        return itemRoomDao.getAllChangedItemsWhereTimeBetween(timeStart, timeEnd)
    }

    suspend fun getMainGuardList(): List<Guards> {
        return itemRoomDao.getMainGuardList()
    }

    suspend fun insertItem(item: Items) {
        itemRoomDao.insertItem(item)
    }

    suspend fun insertGuard(guard: Guards) {
        itemRoomDao.insertGuard(guard)
    }


    suspend fun insertItemList(list: MutableList<Items>) {
        itemRoomDao.insertItemList(list)
    }

    suspend fun insertGuardList(list: MutableList<Guards>) {
        itemRoomDao.insertGuardList(list)
    }


    suspend fun deleteMainItem(item_id: String){
        itemRoomDao.deleteMainItem(item_id)
    }

    suspend fun deleteMainGuard(guardFire_id: String){
        itemRoomDao.deleteMainGuard(guardFire_id)
    }

    suspend fun deleteGuardLate(guard: Guards) {
        itemRoomDao.deleteGuard(guard)
    }




}

