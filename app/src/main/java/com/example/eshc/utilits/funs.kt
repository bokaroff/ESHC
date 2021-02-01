package com.example.eshc.utilits

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.example.eshc.model.Items
import com.google.firebase.firestore.DocumentChange
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


private var swipeBackground = ColorDrawable(Color.RED)

private lateinit var mKey: String
private lateinit var mListItems: MutableList<Items>
private lateinit var mViewHolder: RecyclerView.ViewHolder
private lateinit var mToolbar: Toolbar
private lateinit var mRecyclerView: RecyclerView
private lateinit var mSearchView: SearchView
lateinit var mDutyItem: Items


fun showToast(message: String) {
    Toast.makeText(APP_ACTIVITY, message, Toast.LENGTH_LONG).show()
}

fun saveChangedItemToRoom(item: Items) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            REPOSITORY_ROOM.insertItem(item)
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                e.message?.let { showToast(it) }
            }
        }
    }
}

