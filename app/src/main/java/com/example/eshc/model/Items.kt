package com.example.eshc.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "items_table")
data class Items(
  @PrimaryKey(autoGenerate = true) var entity_id: Int = 0,
  var item_id: String = "",
  var objectName: String = "",
  var objectPhone: String = "",
  var mobilePhone: String = "",
  var kurator: String = "",
  var worker08: String = "",
  var address: String = "",
  var worker15: String = ""
) : Serializable {

}