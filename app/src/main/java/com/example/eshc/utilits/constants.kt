package com.example.eshc.utilits

import com.example.eshc.MainActivity
import com.example.eshc.database.room.ItemRoomDao
import com.example.eshc.database.room.ItemRoomDatabase
import com.example.eshc.database.room.RoomRepository
import com.example.eshc.model.Guards
import com.example.eshc.model.Items
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


const val TAG = "ktx"

const val field_00 = "order00"
const val field_02 = "order02"
const val field_04 = "order04"
const val field_06 = "order06"
const val field_08 = "order08"
const val field_15 = "order15"
const val field_21 = "order21"
const val itemLongTime = "itemLongTime"
const val getAllMainItems = "SELECT * FROM items_table WHERE state = 'main'"
const val getAllMainGuards = "SELECT * FROM guards_table WHERE state = 'main' ORDER BY guardName Asc"
const val getAllChangedItems = "SELECT * FROM items_table WHERE state = 'changed'"
const val singleChangedItem =
    "SELECT * FROM items_table WHERE state = 'changed' and objectName =:name"
const val getAllChangedItemsWhereTimeBetween =
    "SELECT * FROM items_table WHERE state = 'changed'and itemLongTime BETWEEN :timeStart and :timeEnd"
const val getAllGuardsLate = "SELECT * FROM guards_table WHERE state = 'late'"
const val getMainItemList00 =
    "SELECT * FROM items_table WHERE order00 = 'true' AND state = 'main' ORDER BY objectName Asc"
const val getMainItemList02 =
    "SELECT * FROM items_table WHERE order02 = 'true' AND state = 'main' ORDER BY objectName Asc"
const val getMainItemList04 =
    "SELECT * FROM items_table WHERE order04 = 'true' AND state = 'main' ORDER BY objectName Asc"
const val getMainItemList06 =
    "SELECT * FROM items_table WHERE order06 = 'true' AND state = 'main' ORDER BY objectName Asc"
const val getMainItemList08 =
    "SELECT * FROM items_table WHERE order08 = 'true' AND state = 'main' ORDER BY objectName Asc"
const val getMainItemList15 =
    "SELECT * FROM items_table WHERE order15 = 'true' AND state = 'main' ORDER BY objectName Asc"
const val getMainItemList21 =
    "SELECT * FROM items_table WHERE order21 = 'true' AND state = 'main' ORDER BY objectName Asc"
const val stateChanged = "changed"
const val stateMain = "main"
const val stateLate = "late"
const val state = "state"

const val guard_fire_id = "guardFire_id"
const val entity_id = "entity_id"
const val guard_kurator = "guardKurator"
const val guard_name = "guardName"
const val guard_phone = "guardPhone"
const val guard_phone_2 = "guardPhone_2"
const val guard_img = "guard_img"
const val guard_workPlace = "guardWorkPlace"
const val guardLateTime = "serverTimeStamp"

const val item_fire_id = "item_id"
const val item_name = "objectName"
const val item_address = "address"
const val item_phone = "objectPhone"
const val item_mobilePhone = "mobilePhone"
const val item_kurator = "kurator_textView"
const val item_worker08 = "worker08"
const val item_worker15 = "worker15"
const val item_img = "img"
const val item_serverTimeStamp = "serverTimeStamp"

lateinit var APP_ACTIVITY: MainActivity
lateinit var DB: FirebaseFirestore
lateinit var AUTH: FirebaseAuth
lateinit var ITEM: Items
lateinit var GUARD: Guards
lateinit var REPOSITORY_ROOM: RoomRepository
lateinit var ITEM_ROOM_DAO: ItemRoomDao
lateinit var ITEM_ROOM_DATABASE: ItemRoomDatabase
lateinit var optionsItems: FirestoreRecyclerOptions<Items>
lateinit var optionsGuards: FirestoreRecyclerOptions<Guards>


val collectionITEMS_REF = FirebaseFirestore.getInstance()
    .collection("Items")
val collectionGUARDS_REF = FirebaseFirestore.getInstance()
    .collection("Guards")
val collectionSTAFF_REF = FirebaseFirestore.getInstance()
    .collection("Staff")
