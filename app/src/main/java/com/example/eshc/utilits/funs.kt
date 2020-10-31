package com.example.eshc.utilits

import android.widget.Toast
import com.example.eshc.model.Items
import com.google.firebase.firestore.DocumentChange
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun showToast(message: String){
    Toast.makeText(APP_ACTIVITY,message, Toast.LENGTH_LONG).show()
}

fun insertItemChangesRoom() {
    collectionITEMS_REF.addSnapshotListener { value, error ->
        if (value != null) {
            for (dc in value.documentChanges) {
                if (dc.type == DocumentChange.Type.MODIFIED) {
                    ITEM = dc.document.toObject(Items::class.java)
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            ITEM_ROOM_REPOSITORY.insert(ITEM)
                            withContext(Dispatchers.Main) {
                                showToast("Объект ${ITEM.objectName} сохранен")
                            }
                        } catch (e: Exception) {
                            withContext(Dispatchers.Main) {
                                showToast(e.message.toString())
                            }
                        }
                    }
                }
            }
        } else showToast(error?.message.toString())
    }
}
