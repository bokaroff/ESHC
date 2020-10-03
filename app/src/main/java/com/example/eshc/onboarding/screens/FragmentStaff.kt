package com.example.eshc.onboarding.screens

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.eshc.R
import com.example.eshc.databinding.FragmentStaffBinding
import com.example.eshc.utilits.showToast


class FragmentStaff : Fragment() {

    private var _binding: FragmentStaffBinding? = null
    private val mBinding get() = _binding!!
    init {
        showToast("FragmentStaff")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentStaffBinding.inflate(layoutInflater, container, false)

        mBinding.fragmentStaffToolbar.setupWithNavController(findNavController())
        mBinding.fragmentStaffToolbar.title = resources.getString(R.string.staff)
        return mBinding.root
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null



    }

}