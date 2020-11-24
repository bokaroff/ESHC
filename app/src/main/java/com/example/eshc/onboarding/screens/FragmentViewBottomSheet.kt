package com.example.eshc.onboarding.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eshc.databinding.FragmentViewBottomSheetBinding
import com.example.eshc.model.Items
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FragmentViewBottomSheet : BottomSheetDialogFragment() {
    private var _binding: FragmentViewBottomSheetBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var mCurentitem: Items

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentViewBottomSheetBinding.
        inflate(layoutInflater, container, false)
        mCurentitem = arguments?.getSerializable("item") as Items
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialization()
    }

    private fun initialization() {
        mBinding.txtName.text = mCurentitem.objectName
    }

}