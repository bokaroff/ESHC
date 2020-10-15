package com.example.eshc.onboarding.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eshc.adapters.SimpleAdapter
import com.example.eshc.databinding.Fragment00Binding
import com.example.eshc.model.Items
import com.example.eshc.utilits.collectionITEMS_REF
import com.example.eshc.utilits.showToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class Fragment00 : Fragment() {

    private var _binding: Fragment00Binding? = null
    private val mBinding get() = _binding!!
    private lateinit var mRecyclerView: RecyclerView


            override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = Fragment00Binding.inflate(layoutInflater,container,false)
                return mBinding.root
         }


    override fun onStart() {
        super.onStart()
        initFirebase()
    }

        fun initFirebase() {

            mRecyclerView = mBinding.ryFragment00
            mRecyclerView.layoutManager = LinearLayoutManager(context)
            val mList = mutableListOf<Items>()

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val querySnapshot = collectionITEMS_REF
                        .whereEqualTo("order00", "true").get().await()
                    for (snap in querySnapshot) {
                        val item = snap.toObject(Items::class.java)
                        mList.add(item)
                    }
                    withContext(Dispatchers.Main) {
                        mRecyclerView.adapter = SimpleAdapter(mList)
                    }

                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        showToast(e.message.toString())
                    }
                }
            }
        }



          override fun onDestroyView() {
              super.onDestroyView()
              _binding = null
          }
}