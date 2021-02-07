package com.example.eshc.utilits

import android.widget.Toast
import com.example.eshc.model.Items
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


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


