package com.example.eshc.onboarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eshc.R
import com.example.eshc.databinding.Fragment00Binding
import com.example.eshc.databinding.Fragment02Binding
import com.example.eshc.databinding.FragmentViewPagerBinding

class Fragment00 : Fragment() {

    private var _binding: Fragment00Binding? = null
    private val mBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = Fragment00Binding.inflate(layoutInflater,container,false)
        return mBinding.root
    }






    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




}