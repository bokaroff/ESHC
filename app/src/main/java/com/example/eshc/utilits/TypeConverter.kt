package com.example.eshc.utilits

import androidx.room.TypeConverter
import java.util.*

class TypeConverter {

    @TypeConverter
    fun dateToLong(date: Date): Long {
        return date.time
    }

}