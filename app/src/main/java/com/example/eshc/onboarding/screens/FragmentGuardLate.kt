package com.example.eshc.onboarding.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.eshc.R
import com.example.eshc.databinding.FragmentGuardLateBinding


class FragmentGuardLate : Fragment() {
    private var _binding: FragmentGuardLateBinding? = null
    private val mBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGuardLateBinding.inflate(layoutInflater,container,false)

        mBinding.fragmentGuardLateToolbar.setupWithNavController(findNavController())
        mBinding.fragmentGuardLateToolbar.title = resources.getString(R.string.frag_guard_late_toolbar_title)
        return mBinding.root
    }





    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}