package com.example.eshc.onboarding.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eshc.R
import com.example.eshc.adapters.FireGuardAdapter
import com.example.eshc.adapters.FireRecyclerAdapter
import com.example.eshc.databinding.FragmentStaffBinding
import com.example.eshc.model.Guards
import com.example.eshc.model.Items
import com.example.eshc.utilits.*
import com.example.eshc.utilits.adapterFireStore
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


class FragmentStaff : Fragment() {

    private var _binding: FragmentStaffBinding? = null
    private val mBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentStaffBinding.inflate(layoutInflater, container, false)

        mBinding.fragmentStaffToolbar.setupWithNavController(findNavController())
        mBinding.fragmentStaffToolbar.title = resources.getString(R.string.frag_staff_toolbar_title)
        mBinding.ryFragmentStaff.layoutManager = LinearLayoutManager(context)
        mBinding.ryFragmentStaff.adapter = adapterFireGuard

        return mBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initFirebase()
    }

    fun initFirebase() {

        DB = FirebaseFirestore.getInstance()
        queryGuards = DB.collection("Workers").orderBy("guardName", Query.Direction.ASCENDING)
        optionsGuards = FirestoreRecyclerOptions.Builder<Guards>()
            .setQuery(queryGuards, Guards::class.java)
            .build()
        adapterFireGuard = FireGuardAdapter(optionsGuards)

        DB.collection("Workers").addSnapshotListener { value, error ->
        }
    }

    override fun onStart() {
        super.onStart()
        adapterFireGuard.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapterFireGuard.stopListening()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}