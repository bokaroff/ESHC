package com.example.eshc.onboarding.screens.bottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.eshc.R
import com.example.eshc.databinding.FragmentViewBottomSheetBinding
import com.example.eshc.model.Items
import com.example.eshc.utilits.GUARD
import com.example.eshc.utilits.insertGuardLateRoom
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FragmentViewBottomSheet : BottomSheetDialogFragment() {
    private var _binding: FragmentViewBottomSheetBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var mCurentitem: Items

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentViewBottomSheetBinding.
        inflate(layoutInflater, container, false)
        mCurentitem = arguments?.getSerializable("item") as Items
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialization()
    }

    private fun initialization() {
        mBinding.txtName.text = mCurentitem.objectName

        mBinding.containerAddItemLate.setOnClickListener {
            GUARD.guardWorkPlace= mCurentitem.objectName
            GUARD.guardName = mCurentitem.worker08
            GUARD.guardKurator = mCurentitem.kurator
           insertGuardLateRoom(GUARD)
            dismiss()
        }
        mBinding.containerItemUpdate.setOnClickListener {
            findNavController()
                .navigate(R.id.action_fragmentViewBottomSheet_to_updateItemFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}