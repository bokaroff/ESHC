package com.example.eshc.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "guards_table")
data class Guards(
    @PrimaryKey(autoGenerate = true) val entity_id: Int = 0,
    var firestore_id: String = "",
    var guardKurator: String = "",
    var guardName: String = "",
    var guardPhone: String = "",
    var guardPhone_2: String = "",
    var guard_img: String = "",
    var workPlace: String = "",
    var guardLateTime: String = ""
)