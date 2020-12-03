package com.example.eshc.onboarding.screens.mainView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.eshc.adapters.AdapterItems
import com.example.eshc.databinding.Fragment02Binding
import com.example.eshc.utilits.field_02
import com.example.eshc.utilits.getData
import com.example.eshc.utilits.yeah


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
        getData(field_02, yeah, mAdapterItems, mRecyclerView)
    }

    private fun initialization() {
        mRecyclerView = mBinding.rvFragment02
        mAdapterItems = AdapterItems()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
       mRecyclerView.adapter = null
    }
}