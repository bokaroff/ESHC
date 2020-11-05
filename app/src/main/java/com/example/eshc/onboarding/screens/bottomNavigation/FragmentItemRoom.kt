package com.example.eshc.onboarding.screens.bottomNavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.eshc.R
import com.example.eshc.databinding.FragmentItemRoomBinding
import com.example.eshc.utilits.showToast


class FragmentItemRoom : Fragment() {
    private var _binding: FragmentItemRoomBinding? = null
    private val mBinding get() = _binding!!

    init {
        showToast("FragmentItemRoom")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentItemRoomBinding.inflate(layoutInflater, container, false)

        mBinding.fragmentItemRoomToolbar.setupWithNavController(findNavController())
        mBinding.fragmentItemRoomToolbar.title = resources.getString(R.string.frag_itemRoom)

        return mBinding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}