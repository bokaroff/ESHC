package com.example.eshc.utilits

import com.example.eshc.MainActivity
import com.example.eshc.adapters.AdapterGuardLate
import com.example.eshc.adapters.FireGuardAdapter
import com.example.eshc.adapters.FireItemAdapter
import com.example.eshc.database.room.ItemRoomDao
import com.example.eshc.database.room.ItemRoomDatabase
import com.example.eshc.database.room.RoomRepository
import com.example.eshc.model.Guards
import com.example.eshc.model.Items
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore


const val TAG = "ktx"
const val field_00 = "order00"
const val field_02 = "order02"
const val field_04 = "order04"
const val field_06 = "order06"
const val field_08 = "order08"
const val field_15 = "order15"
const val field_21 = "order21"
const val yeah = "true"


const val guard_fire_id = "guardFire_id"
const val guard_kurator = "guardKurator"
const val guard_name = "guardName"
const val guard_phone = "guardPhone"
const val guard_phone_2 = "guardPhone_2"
const val guard_img = "guard_img"
const val guard_workPlace = "guardWorkPlace"
const val item_fire_id = "item_id"
const val item_name = "objectName"
const val item_phone = "objectPhone"
const val item_mobilePhone = "mobilePhone"
const val item_kurator = "kurator"
const val item_worker08 = "worker08"
const val item_address = "address"
const val item_worker15 = "worker15"



lateinit var APP_ACTIVITY: MainActivity
lateinit var ITEM: Items
lateinit var GUARD: Guards
lateinit var DB: FirebaseFirestore
lateinit var REPOSITORY_ROOM: RoomRepository
lateinit var ITEM_ROOM_DAO: ItemRoomDao
lateinit var ITEM_ROOM_DATABASE: ItemRoomDatabase
lateinit var optionsItems: FirestoreRecyclerOptions<Items>
lateinit var optionsGuards: FirestoreRecyclerOptions<Guards>
lateinit var adapterFireGuard: FireGuardAdapter<Guards, FireGuardAdapter.GuardViewHolder>
lateinit var adapterFireItem: FireItemAdapter<Items, FireItemAdapter.ItemViewHolder>
lateinit var adapterGuardLate: AdapterGuardLate




val collectionITEMS_REF = FirebaseFirestore.getInstance()
    .collection("Items")
val collectionGUARDS_REF = FirebaseFirestore.getInstance()
    .collection("Workers")
val collectionSTAFF_REF = FirebaseFirestore.getInstance()
    .collection("Staff")

