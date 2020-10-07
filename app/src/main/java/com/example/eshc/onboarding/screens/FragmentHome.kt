package com.example.eshc.onboarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.eshc.R
import com.example.eshc.databinding.FragmentHomeBinding
import com.example.eshc.databinding.FragmentStaffBinding
import com.example.eshc.utilits.showToast

class FragmentHome : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val mBinding get() = _binding!!
    init {
        showToast("FragmentHome")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        mBinding.fragmentHomeToolbar.setupWithNavController(findNavController())
        mBinding.fragmentHomeToolbar.title = resources.getString(R.string.frag_home)

        return mBinding.root
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null



    }

}