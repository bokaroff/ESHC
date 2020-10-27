package com.example.eshc.onboarding.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eshc.adapters.Adapter
import com.example.eshc.databinding.Fragment06Binding
import com.example.eshc.model.Items
import com.example.eshc.utilits.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class Fragment06 : Fragment() {

    private var _binding: Fragment06Binding? = null
    private val mBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = Fragment06Binding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()

        initFirebase()
    }


    private fun initFirebase () {
        mRecyclerView = mBinding.ryFragment06
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        val mList = mutableListOf<Items>()

        CoroutineScope(Dispatchers.IO).launch {
            try {
               val querySnapshot = collectionITEMS_REF
                    .whereEqualTo("order06", "true").get().await()
                for (snap in querySnapshot) {
                   val item = snap.toObject(Items::class.java)
                    mList.add(item)
                }
                withContext(Dispatchers.Main) {
                   mRecyclerView.adapter = Adapter(mList)
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