package com.example.eshc.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "guards_table")
data class Guards(
    @PrimaryKey(autoGenerate = true) var entity_id: Int = 0,
    var guardFire_id: String = "",
    var guardKurator: String = "",
    var guardName: String = "",
    var guardPhone: String = "",
    var guardPhone_2: String = "",
    var guard_img: String = "",
    var guardWorkPlace: String = "",
    var guardLateTime: String = ""
) : Serializable