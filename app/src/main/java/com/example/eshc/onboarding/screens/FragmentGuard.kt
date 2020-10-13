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
import com.example.eshc.databinding.FragmentGuardBinding
import com.example.eshc.model.Guards
import com.example.eshc.utilits.*
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query


class FragmentGuard : Fragment() {

    private var _binding: FragmentGuardBinding? = null
    private val mBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGuardBinding.inflate(layoutInflater, container, false)

        mBinding.fragmentGuardToolbar.setupWithNavController(findNavController())
        mBinding.fragmentGuardToolbar.title = resources.getString(R.string.frag_guard_toolbar_title)
        mBinding.ryFragmentGuard.layoutManager = LinearLayoutManager(context)
        mBinding.ryFragmentGuard.adapter = adapterFireGuard

        return mBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initFirebase()
    }

    fun initFirebase() {

        queryGuards = collectionGUARDS_REF.orderBy("guardName", Query.Direction.ASCENDING)
        optionsGuards = FirestoreRecyclerOptions.Builder<Guards>()
            .setQuery(queryGuards, Guards::class.java)
            .build()
        adapterFireGuard = FireGuardAdapter(optionsGuards)

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