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
            val map = getNewGuardMap()
            if (map[guard_name] !== null) {
                addNewGuard(map)
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

    private fun getNewGuardMap(): Map<String, Any> {

        val name = etName.text.toString().trim()
        val address = etAddress.text.toString().trim()
        val phone = etPhone.text.toString().trim()
        val mobile = etPhone_2.text.toString().trim()
        val kurator = etKurator.text.toString().trim()
        val map = mutableMapOf<String, String>()

        if (name.isEmpty()) {
            showToast("Введите имя охранника")
        } else {
            map[guard_name] = name
            GUARD.guardName = name
        }

        map[guard_workPlace] = address
        map[guard_phone] = phone
        map[guard_phone_2] = mobile
        map[guard_kurator] = kurator
        map[guard_kurator] = kurator
        map[guard_fire_id] = ""
        map[guard_img] = ""
        map[state] = stateMain
        map[guardLateTime] = ""

        GUARD.guardWorkPlace = address
        GUARD.guardPhone = phone
        GUARD.guardPhone_2 = mobile
        GUARD.guardKurator = kurator
        GUARD.state = stateMain

        return map
    }

    private fun addNewGuard(newGuardMap: Map<String, Any>) {
        val newName = newGuardMap[guard_name].toString()
            .toLowerCase(Locale.ROOT).trim()
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

                val docRef = collectionGUARDS_REF.add(newGuardMap).await()
                val key = docRef.id
                collectionGUARDS_REF.document(key).update(guard_fire_id, key).await()
                GUARD.guardFire_id = key
                GUARD.state = stateMain
                REPOSITORY_ROOM.insertGuard(GUARD)

                 Log.d(TAG, "addNewItem: $key + ${ GUARD.guardName} + ${ GUARD.state}")
                withContext(Dispatchers.Main) {
                    APP_ACTIVITY.navController
                        .navigate(R.id.action_addNewGuardFragment_to_viewPagerFragment)
                    showToast("Добавлен новый охранник ${GUARD.guardName}")
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