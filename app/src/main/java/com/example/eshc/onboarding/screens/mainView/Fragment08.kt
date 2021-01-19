package com.example.eshc.onboarding.screens.mainView

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.eshc.adapters.AdapterItems
import com.example.eshc.databinding.Fragment08Binding
import com.example.eshc.model.Items
import com.example.eshc.utilits.*
import com.google.firebase.firestore.DocumentChange


class Fragment08 : Fragment() {

    private var _binding: Fragment08Binding? = null
    private val mBinding get() = _binding!!
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapterItems: AdapterItems
    private lateinit var mObserveList: Observer<List<Items>>
    private lateinit var mViewModel: Fragment08ViewModel
    private var mutableList = mutableListOf<Items>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = Fragment08Binding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialization()
        getData08()
        insertItemChangesRoom()


        collectionITEMS_REF
            .addSnapshotListener { value, error ->
                if (value != null) {
                    for (dc in value.documentChanges) {
                        if (dc.type == DocumentChange.Type.MODIFIED) {
                            val item = dc.document.toObject(Items::class.java)
                            Log.d(TAG, "mutableList: + ${item.objectName} ")

                            for (i in mutableList){
                                if (i.objectName == item.objectName) {
                                    mAdapterItems.removeItem(i)
                                    Log.d(TAG, "removeItem: + ${mutableList.size} ")

                                }

                            }

                        }
                    }
                } else showToast(error?.message.toString())
            }







    }

    private fun initialization() {
        mRecyclerView = mBinding.rvFragment08
        mAdapterItems = AdapterItems()
    }

    private fun getData08() {
        mObserveList = Observer {
            mutableList = it.toMutableList()
            mAdapterItems.setList(mutableList)
            mRecyclerView.adapter = mAdapterItems
        }
        mViewModel = ViewModelProvider(this)
            .get(Fragment08ViewModel::class.java)
        mViewModel.mainItemList08.observe(this, mObserveList)
    }









    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mViewModel.mainItemList08.removeObserver(mObserveList)
        mRecyclerView.adapter = null
        Log.d(TAG, "stop: $javaClass")
    }
}