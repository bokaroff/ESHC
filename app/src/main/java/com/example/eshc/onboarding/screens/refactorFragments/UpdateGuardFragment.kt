package com.example.eshc.onboarding.screens.refactorFragments

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.eshc.R
import com.example.eshc.databinding.FragmentUpdateGaurdBinding
import com.example.eshc.model.Guards
import com.example.eshc.utilits.*
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil
import java.util.*


class UpdateGuardFragment : Fragment() {

    private var _binding: FragmentUpdateGaurdBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var mToolbar: Toolbar
    private lateinit var mTextView: TextView
    private lateinit var mCurrentGuard: Guards
    private lateinit var etName: EditText
    private lateinit var etAddress: EditText
    private lateinit var etPhone: EditText
    private lateinit var etPhone_2: EditText
    private lateinit var etKurator: EditText
    private lateinit var btnSave: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateGaurdBinding.inflate(
            layoutInflater,
            container, false
        )
        mCurrentGuard = arguments?.getSerializable("guard") as Guards
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialization()
        btnSave.setOnClickListener {
            val guard = getNewGuard()
            if (guard.guardName.isNotEmpty()) {
                updateGuard(guard)
            }
            UIUtil.hideKeyboard(context as Activity)
        }
    }

    private fun initialization() {
        mToolbar = mBinding.fragmentUpdateGuardToolbar
        mTextView = mBinding.fragmentUpdateGuardTextView
        mToolbar.setupWithNavController(findNavController())
        etName = mBinding.fragmentUpdateGuardName
        etAddress = mBinding.fragmentUpdateGuardAddress
        etPhone = mBinding.fragmentUpdateGuardPhone
        etPhone_2 = mBinding.fragmentUpdateGuardMobilePhone
        etKurator = mBinding.fragmentUpdateGuardKurator
        btnSave = mBinding.fragmentUpdateGuardButtonSave

        mTextView.text = mCurrentGuard.guardName
        etName.text.append(mCurrentGuard.guardName)
        etAddress.text.append(mCurrentGuard.guardWorkPlace)
        etPhone.text.append(mCurrentGuard.guardPhone)
        etPhone_2.text.append(mCurrentGuard.guardPhone_2)
        etKurator.text.append(mCurrentGuard.guardKurator)
    }

    private fun getNewGuard(): Guards {
        GUARD = mCurrentGuard

        val name = etName.text.toString().trim()
        val address = etAddress.text.toString().trim()
        val phone = etPhone.text.toString().trim()
        val mobile = etPhone_2.text.toString().trim()
        val kurator = etKurator.text.toString().trim()

        when {
            name.isEmpty() -> {
                GUARD.guardName = ""
                showToast("Введите имя охранника")
            }
            else -> GUARD.guardName = name
        }

        GUARD.guardWorkPlace = address
        GUARD.guardPhone = phone
        GUARD.guardPhone_2 = mobile
        GUARD.guardKurator = kurator
        GUARD.state = stateMain

        return GUARD
    }

    private fun updateGuard(guard: Guards) {
        val newId = guard.guardFire_id
        val newName = guard.guardName.toLowerCase(Locale.ROOT).trim()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val roomList = REPOSITORY_ROOM.getMainGuardList()

                for (doc in roomList) {
                    val oldName = doc.guardName.toLowerCase(Locale.ROOT).trim()
                    val oldId = doc.guardFire_id

                    if (oldName == newName && oldId != newId) {
                        Log.d(TAG, "equal: + $oldName + $newName ")
                        withContext(Dispatchers.Main) {
                            showToast("Охранник с таким именем уже существует")
                        }
                        return@launch
                    }
                }
                collectionITEMS_REF.document(newId)
                    .set(guard, SetOptions.merge()).await()
                REPOSITORY_ROOM.deleteMainItem(newId)
                REPOSITORY_ROOM.insertGuard(guard)
                withContext(Dispatchers.Main) {
                    APP_ACTIVITY.navController
                        .navigate(R.id.action_updateGuardFragment_to_viewPagerFragment)
                    showToast("Данные по объекту ${guard.guardName} изменены")
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