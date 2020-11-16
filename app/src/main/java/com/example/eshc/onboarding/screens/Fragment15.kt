package com.example.eshc.onboarding.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eshc.adapters.Adapter
import com.example.eshc.databinding.Fragment15Binding
import com.example.eshc.model.Items
import com.example.eshc.utilits.*
import com.google.firebase.firestore.DocumentChange
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class Fragment15 : Fragment() {

    private var _binding: Fragment15Binding? = null
    private val mBinding get() = _binding!!
    private lateinit var rv: RecyclerView
    var name: String = "null"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = Fragment15Binding.inflate(layoutInflater, container, false)
        rv = mBinding.ryFragment15
        rv.layoutManager = LinearLayoutManager(context)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        getData()
        insertItemChangesRoom()
        Log.d(TAG, "insertItemChangesRoom3: $name")
    }

    private fun getData() {
        val mList = mutableListOf<Items>()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val querySnapshot = collectionITEMS_REF
                    .whereEqualTo("order15", "true").get().await()
                for (snap in querySnapshot) {
                    val item = snap.toObject(Items::class.java)
                    mList.add(item)
                }
                withContext(Dispatchers.Main) {
                    rv.adapter = Adapter(mList)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    e.message?.let { showToast(it) }
                }
            }
        }
    }

    private fun insertItemChangesRoom(){

      collectionITEMS_REF.addSnapshotListener { value, error ->
            if (value != null) {
                for (dc in value.documentChanges) {
                    if (dc.type == DocumentChange.Type.MODIFIED) {
                        ITEM = dc.document.toObject(Items::class.java)
                            name = ITEM.objectName
                        Log.d(TAG, "insertItemChangesRoom: $name")
                        CoroutineScope(Dispatchers.IO).launch {
                            try {
                                REPOSITORY.insertItem(ITEM)
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
        Log.d(TAG, "insertItemChangesRoom2: $name ")

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        rv.adapter = null
    }
}

