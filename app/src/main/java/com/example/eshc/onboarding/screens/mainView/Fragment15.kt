package com.example.eshc.onboarding.screens.mainView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.eshc.adapters.AdapterItems
import com.example.eshc.databinding.Fragment15Binding
import com.example.eshc.model.Items
import com.example.eshc.utilits.*
import com.google.firebase.firestore.DocumentChange
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Fragment15 : Fragment() {

    private var _binding: Fragment15Binding? = null
    private val mBinding get() = _binding!!
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapterItems: AdapterItems

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = Fragment15Binding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialization()
        getData(field_15, yeah, mAdapterItems, mRecyclerView)
        insertItemChangesRoom()
    }

    private fun initialization() {
        mRecyclerView = mBinding.rvFragment15
        mAdapterItems = AdapterItems()
    }

    private fun insertItemChangesRoom(){
        collectionITEMS_REF.addSnapshotListener { value, error ->
            if (value != null) {
                for (dc in value.documentChanges) {
                    if (dc.type == DocumentChange.Type.MODIFIED) {
                        ITEM = dc.document.toObject(Items::class.java)

                        CoroutineScope(Dispatchers.IO).launch {
                            try {
                                REPOSITORY_ROOM.insertItem(ITEM)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mRecyclerView.adapter = null
    }
}

