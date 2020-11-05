package com.example.eshc.onboarding.screens.bottomNavigation

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
import com.example.eshc.utilits.adapterFireGuard
import com.example.eshc.utilits.collectionGUARDS_REF
import com.firebase.ui.firestore.FirestoreRecyclerOptions


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
        getData()
    }

    private fun getData() {
        val queryGuards = collectionGUARDS_REF
        val optionsGuards = FirestoreRecyclerOptions.Builder<Guards>()
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