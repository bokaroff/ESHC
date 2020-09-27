package com.example.eshc.onboarding.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.eshc.R
import com.example.eshc.databinding.Fragment00Binding
import com.example.eshc.databinding.Fragment15Binding

class Fragment15 : Fragment() {

    private var _binding: Fragment15Binding? = null
    private val mBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = Fragment15Binding.inflate(layoutInflater,container,false)
        return mBinding.root
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}