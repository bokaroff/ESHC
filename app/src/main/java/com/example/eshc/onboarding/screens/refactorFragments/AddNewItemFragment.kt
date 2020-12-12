package com.example.eshc.onboarding.screens.refactorFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.eshc.R
import com.example.eshc.databinding.FragmentAddNewItemBinding
import com.example.eshc.databinding.FragmentUpdateItemBinding

class AddNewItemFragment : Fragment() {
    private var _binding: FragmentAddNewItemBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var mToolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddNewItemBinding.inflate(layoutInflater,
            container,false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialization()
        mBinding.fragmentAddNewItemAddress.hint = "где то далеко в Айтем"
        mBinding.fragmentAddNewItemName.hint = "Шишков Алексей в Айтем"
    }

    private fun initialization() {
        mToolbar = mBinding.fragmentAddNewItemToolbar
        mToolbar.setupWithNavController(findNavController())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}