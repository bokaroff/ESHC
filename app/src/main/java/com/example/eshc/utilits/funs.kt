package com.example.eshc.utilits

import android.widget.Toast
import com.example.eshc.model.Guards
import com.example.eshc.model.Items
import com.google.firebase.firestore.DocumentChange
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun showToast(message: String) {
    Toast.makeText(APP_ACTIVITY, message, Toast.LENGTH_LONG).show()
}



fun insertGuardLateRoom(guard: Guards) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            ITEM_ROOM_REPOSITORY.insertGuardLate(guard)
            withContext(Dispatchers.Main) {
                showToast("Охранник ${guard.guardName} сохранен как опоздавщий")
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                showToast(e.message.toString())
            }
        }
    }
}
