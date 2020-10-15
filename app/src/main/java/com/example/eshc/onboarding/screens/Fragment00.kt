package com.example.eshc.onboarding.screens

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eshc.R
import com.example.eshc.adapters.FireItemAdapter
import com.example.eshc.adapters.SimpleAdapter
import com.example.eshc.databinding.Fragment00Binding
import com.example.eshc.databinding.Fragment02Binding
import com.example.eshc.databinding.FragmentViewPagerBinding
import com.example.eshc.model.Items
import com.example.eshc.utilits.*
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception

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
            var mList = mutableListOf<Items>()

            /*
            collectionITEMS_REF.orderBy("objectName", Query.Direction.ASCENDING)
                .whereEqualTo("order00", "true").get().addOnSuccessListener {
                    for (snapShot in it) {

                        val item = snapShot.toObject(Items::class.java)
                        //  Log.d(TAG, "initFirebase ${item.objectName.toString()}" )
                        mList.add(item)
                    }
                    mRecyclerView.adapter = SimpleAdapter(mList)
                }

 */
                CoroutineScope(Dispatchers.IO).launch {
                   try {
                        val querySnapshot = collectionITEMS_REF.orderBy("objectName",
                            Query.Direction.ASCENDING)
                            .whereEqualTo("order00", "true").get().await()
                     for(snap in querySnapshot){
                         val item = snap.toObject(Items::class.java)
                         mList.add(item)
                     }
                       withContext(Dispatchers.Main){
                           mRecyclerView.adapter = SimpleAdapter(mList)
                       }

                   }catch (e : Exception){
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