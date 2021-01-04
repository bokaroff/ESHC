package com.example.eshc.utilits

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.example.eshc.adapters.AdapterGuard
import com.example.eshc.model.Guards
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
private lateinit var mAdapter: AdapterGuard
private lateinit var deleteIcon: Drawable


fun showToast(message: String) {
    Toast.makeText(APP_ACTIVITY, message, Toast.LENGTH_LONG).show()
}

fun insertGuardLateRoom(guard: Guards) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            REPOSITORY_ROOM.insertGuardLate(guard)
            withContext(Dispatchers.Main) {
                showToast("Охранник ${guard.guardName} сохранен как опоздавший")
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                e.message?.let { showToast(it) }
            }
        }
    }
}



 fun insertItemChangesRoom(field: String){
    collectionITEMS_REF.whereEqualTo(field, "true")
        .addSnapshotListener { value, error ->
            if (value != null) {
                for (dc in value.documentChanges) {
                    if (dc.type == DocumentChange.Type.MODIFIED) {
                        val item = dc.document.toObject(Items::class.java)

                        CoroutineScope(Dispatchers.IO).launch {
                            try {
                                item.state = stateChanged
                                REPOSITORY_ROOM.insertMainItem(item)
                                Log.d(TAG, "insertItemChangesRoom: + ${item.state}")
                            } catch (e: Exception) {
                                withContext(Dispatchers.Main) {
                                    e.message?.let { showToast(it) }
                                }
                            }
                        }
                    }
                }
            } else showToast(error?.message.toString())
        }
}

