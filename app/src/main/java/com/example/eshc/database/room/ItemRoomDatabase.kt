package com.example.eshc.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.eshc.model.Guards
import com.example.eshc.model.Items


@Database(entities = [Items::class, Guards::class], version = 1, exportSchema = false)
abstract class ItemRoomDatabase : RoomDatabase() {

    abstract fun getItemRoomDao(): ItemRoomDao

    companion object {

        @Volatile
        private var database: ItemRoomDatabase? = null

        @Synchronized
        fun getInstance(context: Context): ItemRoomDatabase {
            return if (database == null) {
                database = Room.databaseBuilder(
                    context,
                    ItemRoomDatabase::class.java,
                    "ESHC_DB"
                ).build()
                database as ItemRoomDatabase
            } else database as ItemRoomDatabase
        }
    }
}

