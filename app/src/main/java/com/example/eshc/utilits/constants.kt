package com.example.eshc.utilits

import com.example.eshc.MainActivity
import com.example.eshc.adapters.FireGuardAdapter
import com.example.eshc.adapters.FireItemAdapter
import com.example.eshc.adapters.SimpleAdapter
import com.example.eshc.model.Guards
import com.example.eshc.model.Items
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

const val TAG = "kotlin"

lateinit var APP_ACTIVITY: MainActivity
lateinit var DB: FirebaseFirestore
lateinit var optionsItems: FirestoreRecyclerOptions<Items>
lateinit var optionsGuards: FirestoreRecyclerOptions<Guards>
lateinit var adapterFireItem: FireItemAdapter<Items, FireItemAdapter.ItemViewHolder>
lateinit var adapterFireGuard: FireGuardAdapter<Guards, FireGuardAdapter.GuardViewHolder>
lateinit var queryItems: Query
lateinit var queryGuards: Query
lateinit var ITEMS:Items

val collectionITEMS_REF = FirebaseFirestore.getInstance().collection("Items")
    .orderBy("objectName", Query.Direction.ASCENDING)
val collectionGUARDS_REF = FirebaseFirestore.getInstance().collection("Workers")
val collectionSTAFF_REF = FirebaseFirestore.getInstance().collection("Staff")
