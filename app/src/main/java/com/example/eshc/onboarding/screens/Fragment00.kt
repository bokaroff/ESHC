package com.example.eshc.onboarding.screens

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eshc.R
import com.example.eshc.adapters.FireItemAdapter
import com.example.eshc.adapters.SimpleAdapter
import com.example.eshc.databinding.Fragment00Binding
import com.example.eshc.databinding.Fragment02Binding
import com.example.eshc.databinding.FragmentViewPagerBinding
import com.example.eshc.model.Items
import com.example.eshc.utilits.*
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query

class Fragment00 : Fragment() {

    private var _binding: Fragment00Binding? = null
    private val mBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = Fragment00Binding.inflate(layoutInflater,container,false)
        mBinding.ryFragment00.layoutManager = LinearLayoutManager(context)
       // mBinding.ryFragment00.adapter = adapterSimple

        return mBinding.root
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initFirebase()
    }

    fun initFirebase() {



   collectionITEMS_REF.orderBy("objectName", Query.Direction.ASCENDING)
            .whereEqualTo("order00", "true").get().addOnSuccessListener {
           for(snapShot in it){

               val item = snapShot.toObject(Items::class.java)
               Log.d(TAG, "initFirebase ${item.objectName.toString()}" )
           }
       }

    }

    override fun onStart() {
        super.onStart()
       // adapterFireItem.startListening()
    }

    override fun onStop() {
        super.onStop()
      //  adapterFireItem.stopListening()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}