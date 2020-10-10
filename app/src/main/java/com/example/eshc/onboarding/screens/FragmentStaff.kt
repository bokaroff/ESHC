package com.example.eshc.onboarding.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.eshc.R
import com.example.eshc.databinding.FragmentStaffBinding


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

        return mBinding.root
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}