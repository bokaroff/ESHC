package com.example.eshc.onboarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.eshc.R
import com.example.eshc.databinding.Fragment00Binding
import com.example.eshc.databinding.FragmentStaffLateBinding


class FragmentStaffLate : Fragment() {
    private var _binding: FragmentStaffLateBinding? = null
    private val mBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentStaffLateBinding.inflate(layoutInflater,container,false)

        mBinding.fragmentStaffLateToolbar.setupWithNavController(findNavController())
        mBinding.fragmentStaffLateToolbar.title = getString(R.string.staff_late)


        return mBinding.root
    }





    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }



}