package com.example.eshc.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "items_table")
data class Items(
  @PrimaryKey(autoGenerate = true) val entity_id: Int = 0,
  val item_id: String = "",
  val objectName: String = "",
  val objectPhone: String = "",
  val mobilePhone: String = "",
  val kurator: String = "",
  val worker08: String = "",
  val address: String = "",
  val worker15: String = ""
) : Serializable {

}