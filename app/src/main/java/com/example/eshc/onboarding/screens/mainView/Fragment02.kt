package com.example.eshc.onboarding.screens.mainView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.eshc.adapters.AdapterItems
import com.example.eshc.databinding.Fragment02Binding
import com.example.eshc.model.Items
import com.example.eshc.utilits.collectionITEMS_REF
import com.example.eshc.utilits.showToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class Fragment02 : Fragment() {

    private var _binding: Fragment02Binding? = null
    private val mBinding get() = _binding!!
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapterItems: AdapterItems

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = Fragment02Binding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialization()
        getData()
    }

    private fun initialization() {
        mRecyclerView = mBinding.rvFragment02
        mAdapterItems = AdapterItems()
    }

    private fun getData() {

        val mList = mutableListOf<Items>()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val querySnapshot = collectionITEMS_REF
                    .whereEqualTo("order02", "true").get().await()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
       mRecyclerView.adapter = null
    }
}