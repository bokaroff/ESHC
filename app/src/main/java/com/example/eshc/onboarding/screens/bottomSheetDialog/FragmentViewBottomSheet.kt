package com.example.eshc.onboarding.screens.bottomSheetDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eshc.R
import com.example.eshc.databinding.FragmentViewBottomSheetBinding
import com.example.eshc.model.Guards
import com.example.eshc.model.Items
import com.example.eshc.utilits.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class FragmentViewBottomSheet : BottomSheetDialogFragment() {
    private var _binding: FragmentViewBottomSheetBinding? = null
    private val mBinding get() = _binding!!
    private var stringTime: String = String()
    private lateinit var mCurrentItem: Items

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentViewBottomSheetBinding.inflate(layoutInflater, container, false)
        mCurrentItem = arguments?.getSerializable("item") as Items
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialise()
        clicks()
    }

    /*
    override fun onStart() {
        super.onStart()
        initialise()
        clicks()
    }

     */

    private fun initialise() {
        mBinding.txtName.text = mCurrentItem.objectName
    }

    private fun clicks() {
        mBinding.containerAddItemLate.setOnClickListener {

            stringTime = SimpleDateFormat("HH:mm, dd MMM.yyyy", Locale.getDefault())
                .format(Date())
            val longTime = Date().time
            GUARD.guardWorkPlace = mCurrentItem.objectName
            GUARD.guardName = mCurrentItem.worker08
            GUARD.guardKurator = mCurrentItem.kurator
            GUARD.serverTimeStamp = stringTime
            GUARD.guardLongTime = longTime
            GUARD.state = stateLate
            insertGuardLateRoom(GUARD)
            dismiss()
        }

        mBinding.containerAddNewItemLate.setOnClickListener {
            APP_ACTIVITY.navController
                .navigate(R.id.action_fragmentViewBottomSheet_to_fragmentGuardAddNewLate, arguments)
        }

        mBinding.containerItemUpdate.setOnClickListener {
            APP_ACTIVITY.navController
                .navigate(R.id.action_fragmentViewBottomSheet_to_updateItemFragment, arguments)
        }
        mBinding.containerItemAdd.setOnClickListener {
            APP_ACTIVITY.navController
                .navigate(R.id.action_fragmentViewBottomSheet_to_addNewItemFragment)
        }
    }

    private fun insertGuardLateRoom(guard: Guards) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                REPOSITORY_ROOM.insertGuard(guard)
                withContext(Dispatchers.Main) {
                    showToast("Охранник ${guard.guardName} сохранен как опоздавший")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    e.message?.let { showToast(it) }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}