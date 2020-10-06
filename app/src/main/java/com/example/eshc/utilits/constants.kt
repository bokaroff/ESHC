package com.example.eshc.utilits

import com.example.eshc.MainActivity
import com.example.eshc.model.Items
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

lateinit var APP_ACTIVITY: MainActivity

val FIRESTORE_INSTANCE_ITEMS = FirebaseFirestore.getInstance()
    .collection("Items")
val FIRESTORE_INSTANCE = FirebaseFirestore.getInstance()

val QUERY = FIRESTORE_INSTANCE_ITEMS.
orderBy("objectName", com.google.firebase.firestore.Query.Direction.DESCENDING)

val OPTIONS = FirestoreRecyclerOptions.Builder<Items>()
    .setQuery(QUERY, Items::class.java)
    .build()