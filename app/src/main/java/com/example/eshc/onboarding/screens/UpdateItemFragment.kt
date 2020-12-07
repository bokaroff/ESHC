package com.example.eshc.onboarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.eshc.R
import com.example.eshc.databinding.FragmentGuardBinding
import com.example.eshc.databinding.FragmentUpdateItemBinding

class UpdateItemFragment : Fragment() {
    private var _binding: FragmentUpdateItemBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var mToolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateItemBinding.inflate(layoutInflater,
            container,false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialization()
        mBinding.fragmentUpdateItemEdtxtAddress.hint = "где то далеко в Айтем"
        mBinding.fragmentUpdateItemEdtxtName.hint = "Шишков Алексей в Айтем"
    }

    private fun initialization() {
        mToolbar = mBinding.fragmentUpdateItemToolbar
        mToolbar.setupWithNavController(findNavController())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}