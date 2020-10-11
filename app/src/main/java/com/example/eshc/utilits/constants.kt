package com.example.eshc.utilits

import com.example.eshc.MainActivity
import com.example.eshc.adapters.FireGuardAdapter
import com.example.eshc.adapters.FireRecyclerAdapter
import com.example.eshc.model.Guards
import com.example.eshc.model.Items
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

lateinit var APP_ACTIVITY: MainActivity
lateinit var DB: FirebaseFirestore
lateinit var optionsItems: FirestoreRecyclerOptions<Items>
lateinit var optionsGuards: FirestoreRecyclerOptions<Guards>
lateinit var adapterFireStore: FireRecyclerAdapter<Items, FireRecyclerAdapter.ItemViewHolder>
lateinit var adapterFireGuard: FireGuardAdapter<Guards, FireGuardAdapter.GuardViewHolder>
lateinit var query: Query
lateinit var queryGuards: Query
lateinit var ITEMS:Items

val collectionITEMS = FirebaseFirestore.getInstance().collection("Items")

