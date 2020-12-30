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
import com.example.eshc.databinding.FragmentAddNewItemBinding
import com.example.eshc.utilits.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil
import java.util.*

class AddNewItemFragment : Fragment() {
    private var _binding: FragmentAddNewItemBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var mToolbar: Toolbar
    private lateinit var mEdtxtObjectName: EditText
    private lateinit var mEdtxtAddress: EditText
    private lateinit var mEdtxtPhone: EditText
    private lateinit var mEdtxtMobile: EditText
    private lateinit var mEdtxtKurator: EditText
    private lateinit var mButtonSave: Button

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
        mButtonSave.setOnClickListener {
            val map = getNewItemMap()
            if (map[item_name] !== null) {
                addNewItem(map)
            }


            // showToast("Добавлен новый объект ${map[item_name]}")
            //  APP_ACTIVITY.navController
            //       .navigate(R.id.action_addNewItemFragment_to_viewPagerFragment)
            UIUtil.hideKeyboard(context as Activity)
        }
    }

    private fun initialization() {
        mToolbar = mBinding.fragmentAddNewItemToolbar
        mToolbar.setupWithNavController(findNavController())
        mEdtxtObjectName = mBinding.fragmentAddNewItemName
        mEdtxtAddress = mBinding.fragmentAddNewItemAddress
        mEdtxtPhone = mBinding.fragmentAddNewItemPhone
        mEdtxtMobile = mBinding.fragmentAddNewItemMobilePhone
        mEdtxtKurator = mBinding.fragmentAddNewItemKurator
        mButtonSave = mBinding.fragmentAddNewItemButtonAdd
    }

    private fun getNewItemMap(): Map<String, Any> {
        val name = mEdtxtObjectName.text.toString().trim()
        val address = mEdtxtAddress.text.toString().trim()
        val phone = mEdtxtPhone.text.toString().trim()
        val mobile = mEdtxtMobile.text.toString().trim()
        val kurator = mEdtxtKurator.text.toString().trim()
        val map = mutableMapOf<String, String>()

        if (name.isEmpty()) {
            showToast("Введите имя объекта")
        } else {
            map[item_name] = name
            ITEM.objectName = name
        }
        map[item_address] = address
        map[item_phone] = phone
        map[item_mobilePhone] = mobile
        map[item_kurator] = kurator
        map[item_fire_id] = ""
        map[item_worker08] = ""
        map[item_worker15] = ""
        map[item_img] = ""
        map[field_00] = ""
        map[field_02] = ""
        map[field_04] = ""
        map[field_06] = ""
        map[field_08] = ""
        map[field_15] = ""
        map[field_21] = ""
        map[item_serverTimeStamp] = ""

        ITEM.address = address
        ITEM.state = stateMain

        return map
    }


    private fun addNewItem(newItemMap: Map<String, Any>) {
        val newName = newItemMap[item_name].toString()
            .toLowerCase(Locale.ROOT).trim()
        Log.d(TAG, "newName: + $newName  ")
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val roomList =  REPOSITORY_ROOM.getMainItemList()
                Log.d(TAG, "roomList: + ${roomList.size}  ")

                for (doc in roomList) {
                    val oldName = doc.objectName
                        .toLowerCase(Locale.ROOT).trim()

                    if (oldName == newName) {
                        Log.d(TAG, "equal: + $oldName + $newName ")
                        withContext(Dispatchers.Main) {
                            showToast(" Объект с таким именем уже существует")
                        }
                        return@launch
                    }
                }

             val docRef =   collectionITEMS_REF.add(newItemMap).await()
                val key = docRef.id
                collectionITEMS_REF.document(key).update(item_fire_id, key).await()
                ITEM.item_id = key
                REPOSITORY_ROOM.insertItem(ITEM)
                Log.d(TAG, "addNewItem: ${ITEM.item_id} + ${ITEM.objectName} + ${ITEM.state}")

            } catch (e: Exception){
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