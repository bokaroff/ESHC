package com.example.eshc.onboarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eshc.R
import com.example.eshc.databinding.FragmentPopUpBinding
import com.example.eshc.databinding.FragmentUpdateItemBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PopUpFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentPopUpBinding? = null
    private val mBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPopUpBinding.inflate(layoutInflater, container,false)
        return mBinding.root
    }

}