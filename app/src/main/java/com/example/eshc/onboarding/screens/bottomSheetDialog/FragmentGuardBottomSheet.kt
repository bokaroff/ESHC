package com.example.eshc.onboarding.screens.bottomSheetDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.eshc.R
import com.example.eshc.databinding.FragmentGuardBottomSheetBinding
import com.example.eshc.model.Guards
import com.example.eshc.utilits.APP_ACTIVITY
import com.example.eshc.utilits.showToast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FragmentGuardBottomSheet : BottomSheetDialogFragment() {
    private var _binding: FragmentGuardBottomSheetBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var mCurrentItem: Guards

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGuardBottomSheetBinding.
        inflate(layoutInflater, container, false)
        mCurrentItem = arguments?.getSerializable("guard") as Guards
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialization()
        clicks()
    }

    private fun initialization() {
        mBinding.txtName.text = mCurrentItem.guardName
    }

    private fun clicks() {
        mBinding.containerGuardAdd.setOnClickListener {
            APP_ACTIVITY.navController
                .navigate(R.id.action_fragmentGuardBottomSheet_to_addNewGuardFragment)
        }
        mBinding.containerGuardUpdate.setOnClickListener {
            APP_ACTIVITY.navController
                .navigate(R.id.action_fragmentGuardBottomSheet_to_updateGuardFragment,
                    arguments)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}