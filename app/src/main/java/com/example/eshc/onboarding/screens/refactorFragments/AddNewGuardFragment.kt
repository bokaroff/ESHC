package com.example.eshc.onboarding.screens.refactorFragments

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.eshc.R
import com.example.eshc.databinding.FragmentAddNewGuardBinding
import com.example.eshc.model.Guards
import com.example.eshc.utilits.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil
import java.util.*

class AddNewGuardFragment : Fragment() {
    private var _binding: FragmentAddNewGuardBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var mToolbar: Toolbar
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
        // Inflate the layout for this fragment
        _binding = FragmentAddNewGuardBinding.inflate(
            layoutInflater,
            container, false
        )
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialization()
        btnSave.setOnClickListener {
            val guard = getNewGuard()
            if (guard.guardName.isNotEmpty()) {
                addNewGuard(guard)
            }
            UIUtil.hideKeyboard(context as Activity)
        }
    }

    private fun initialization() {
        mToolbar = mBinding.fragmentAddNewGuardToolbar
        mToolbar.setupWithNavController(findNavController())
        etName = mBinding.fragmentAddNewGuardName
        etAddress = mBinding.fragmentAddNewGuardAddress
        etPhone = mBinding.fragmentAddNewGuardPhone
        etPhone_2 = mBinding.fragmentAddNewGuardMobilePhone
        etKurator = mBinding.fragmentAddNewGuardKurator
        btnSave = mBinding.fragmentAddNewGuardButtonAdd
    }

    private fun getNewGuard(): Guards {

        val name = etName.text.toString().trim()
        val address = etAddress.text.toString().trim()
        val phone = etPhone.text.toString().trim()
        val mobile = etPhone_2.text.toString().trim()
        val kurator = etKurator.text.toString().trim()

        when {
            name.isEmpty() ->{
                GUARD.guardName = ""
                showToast("Введите имя охранника")
            } else -> GUARD.guardName = name
        }

        GUARD.guardWorkPlace = address
        GUARD.guardPhone = phone
        GUARD.guardPhone_2 = mobile
        GUARD.guardKurator = kurator
        GUARD.state = stateMain

        return GUARD
    }

    private fun addNewGuard(guard: Guards) {
        val newName = guard.guardName.toLowerCase(Locale.ROOT).trim()
        Log.d(TAG, "newName: + $newName  ")
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val roomList = REPOSITORY_ROOM.getMainGuardList()
                Log.d(TAG, "roomList: + ${roomList.size}  ")

                for (doc in roomList) {
                    val oldName = doc.guardName
                        .toLowerCase(Locale.ROOT).trim()

                    if (oldName == newName) {
                        Log.d(TAG, "equal: + $oldName + $newName ")
                        withContext(Dispatchers.Main) {
                            showToast("Охранник с таким именем уже существует")
                        }
                        return@launch
                    }
                }

                val docRef = collectionGUARDS_REF.add(guard).await()
                val key = docRef.id
                collectionGUARDS_REF.document(key).update(guard_fire_id, key).await()
                guard.guardFire_id = key
                guard.state = stateMain
                REPOSITORY_ROOM.insertGuard(guard)


                 Log.d(TAG, "addNewItem: $key + ${ guard.guardName} + ${ guard.state}")
                withContext(Dispatchers.Main) {
                    APP_ACTIVITY.navController
                        .navigate(R.id.action_addNewGuardFragment_to_viewPagerFragment)
                    showToast("Добавлен новый охранник ${guard.guardName}")
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