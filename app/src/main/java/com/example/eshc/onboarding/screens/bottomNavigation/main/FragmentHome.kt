package com.example.eshc.onboarding.screens.bottomNavigation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.eshc.databinding.FragmentHomeBinding
import com.example.eshc.utilits.APP_ACTIVITY
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FragmentHome : BottomSheetDialogFragment() {




    private var _binding: FragmentHomeBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var mToolbar: Toolbar
    private lateinit var mRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialization()
    }

    private fun initialization() {
     //   APP_ACTIVITY.bottomNavigationView.setupWithNavController(APP_ACTIVITY.navController)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}