package com.example.eshc.onboarding.screens.bottomSheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.eshc.R
import com.example.eshc.databinding.FragmentGuardBottomSheetBinding
import com.example.eshc.databinding.FragmentViewBottomSheetBinding
import com.example.eshc.model.Guards
import com.example.eshc.model.Items
import com.example.eshc.utilits.GUARD
import com.example.eshc.utilits.insertGuardLateRoom
import com.example.eshc.utilits.showToast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FragmentGuardBottomSheet : BottomSheetDialogFragment() {
    private var _binding: FragmentGuardBottomSheetBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var mCurentitem: Guards

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGuardBottomSheetBinding.
        inflate(layoutInflater, container, false)
        mCurentitem = arguments?.getSerializable("guard") as Guards
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialization()
    }

    private fun initialization() {
        mBinding.txtName.text = mCurentitem.guardName

        mBinding.containerGuardAdd.setOnClickListener {
                    showToast("containerGuardAdd")
        }
        mBinding.containerGuardUpdate.setOnClickListener {
            findNavController()
                .navigate(R.id.action_fragmentGuardBottomSheet_to_updateGuardFragment,
                    arguments)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}