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
import com.example.eshc.databinding.FragmentViewBinding
import com.example.eshc.utilits.showToast

class FragmentView : Fragment() {
    private var _binding: FragmentViewBinding? = null
    private val mBinding get() = _binding!!
    init {
        showToast("FragmentView")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentViewBinding.inflate(layoutInflater, container, false)

        mBinding.fragmentViewToolbar.setupWithNavController(findNavController())
        mBinding.fragmentViewToolbar.title = resources.getString(R.string.frag_view)

        return mBinding.root
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null



    }

}