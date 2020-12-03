package com.example.eshc.utilits

import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.eshc.adapters.AdapterItems
import com.example.eshc.model.Guards
import com.example.eshc.model.Items
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

fun showToast(message: String) {
    Toast.makeText(APP_ACTIVITY, message, Toast.LENGTH_LONG).show()
}



fun insertGuardLateRoom(guard: Guards) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            REPOSITORY_ROOM.insertGuardLate(guard)
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




 fun getData(field: String, value: String,
             mAdapterItems: AdapterItems,
             mRecyclerView: RecyclerView ) {
     val mList = mutableListOf<Items>()

    CoroutineScope(Dispatchers.IO).launch {
        try {
            val querySnapshot = collectionITEMS_REF
                .whereEqualTo(field, value).get().await()
            for (snap in querySnapshot) {
                val item = snap.toObject(Items::class.java)
                mList.add(item)
            }
            withContext(Dispatchers.Main) {
                mAdapterItems.setList(mList)
                mRecyclerView.adapter = mAdapterItems
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                e.message?.let { showToast(it) }
            }
        }
    }
}

