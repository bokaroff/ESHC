package com.example.eshc.utilits

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.example.eshc.adapters.AdapterGuard
import com.example.eshc.model.Guards
import com.example.eshc.model.Items
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

/*

fun deleteItemFirestore(items: Items) {
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





fun getItemFire(
    field: String, value: String,
    mAdapterItems: AdapterItems,
    mRecyclerView: RecyclerView
) {
    val mList = mutableListOf<Items>()

    CoroutineScope(Dispatchers.IO).launch {
        val source = Source.CACHE
        try {
            val querySnapshot = collectionITEMS_REF
                .orderBy("objectName", Query.Direction.ASCENDING)
                .whereEqualTo(field, value).get(source).await()
            for (snap in querySnapshot) {
                val item = snap.toObject(Items::class.java)
                mList.add(item)
                Log.d(TAG, "mList  + ${mList.size}")
            }
            withContext(Dispatchers.Main) {
                mAdapterItems.setList(mList)
                mRecyclerView.adapter = mAdapterItems
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Log.d(TAG, "error  + $e")
                e.message?.let { showToast(it) }
            }
        }
    }


}




suspend fun getFire(
    field: String, value: String,
    mAdapterItems: AdapterItems,
    mRecyclerView: RecyclerView
): MutableList<Items> {
    var mList = mutableListOf<Items>()

    var a = CoroutineScope(Dispatchers.IO).async {
        val source = Source.CACHE
        try {
            val querySnapshot = collectionITEMS_REF
                .orderBy("objectName", Query.Direction.ASCENDING)
                .whereEqualTo(field, value).get(source).await()
            for (snap in querySnapshot) {
                val item = snap.toObject(Items::class.java)
                mList.add(item)
                Log.d(TAG, "mList  + ${mList.size}")
            }
            withContext(Dispatchers.Main) {
                mAdapterItems.setList(mList)
                mRecyclerView.adapter = mAdapterItems
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Log.d(TAG, "error  + $e")
                e.message?.let { showToast(it) }
            }
        }
        mList
    }


    mList = a.await()

    Log.d(TAG, "mList_2  + ${mList.size}")
    return mList
}


 */
