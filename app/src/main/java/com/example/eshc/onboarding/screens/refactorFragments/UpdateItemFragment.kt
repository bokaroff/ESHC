package com.example.eshc.onboarding.screens.refactorFragments

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.eshc.R
import com.example.eshc.databinding.FragmentUpdateItemBinding
import com.example.eshc.model.Items
import com.example.eshc.utilits.*
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil
import java.util.*

class UpdateItemFragment : Fragment() {

    private var _binding: FragmentUpdateItemBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var mToolbar: Toolbar
    private lateinit var mCurrentItem: Items
    private lateinit var mEtName: EditText
    private lateinit var mEtAddress: EditText
    private lateinit var mEtPhone: EditText
    private lateinit var mEtMobile: EditText
    private lateinit var mEtKurator: EditText
    private lateinit var checkBox08: CheckBox
    private lateinit var checkBox15: CheckBox
    private lateinit var checkBox21: CheckBox
    private lateinit var checkBox00: CheckBox
    private lateinit var checkBox02: CheckBox
    private lateinit var checkBox04: CheckBox
    private lateinit var checkBox06: CheckBox
    private lateinit var mButtonSave: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateItemBinding.inflate(
            layoutInflater,
            container, false
        )
        mCurrentItem = arguments?.getSerializable("item") as Items
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialization()
        mButtonSave.setOnClickListener {
            val item = getNewItem()
            updateItem(item)
            UIUtil.hideKeyboard(context as Activity)
        }
    }

    private fun initialization() {
        mToolbar = mBinding.fragmentUpdateItemToolbar
        mToolbar.setupWithNavController(findNavController())
        mEtName = mBinding.fragmentUpdateItemName
        mEtAddress = mBinding.fragmentUpdateItemAddress
        mEtPhone = mBinding.fragmentUpdateItemObjectPhone
        mEtMobile = mBinding.fragmentUpdateItemMobilePhone
        mEtKurator = mBinding.fragmentUpdateItemKurator
        checkBox08 = mBinding.fragmentUpdateItemField08
        checkBox15 = mBinding.fragmentUpdateItemField15
        checkBox21 = mBinding.fragmentUpdateItemField21
        checkBox00 = mBinding.fragmentUpdateItemField00
        checkBox02 = mBinding.fragmentUpdateItemField02
        checkBox04 = mBinding.fragmentUpdateItemField04
        checkBox06 = mBinding.fragmentUpdateItemField06
        mButtonSave = mBinding.fragmentUpdateNewItemButton

        mEtName.text.append(mCurrentItem.objectName)
        mEtAddress.text.append(mCurrentItem.address)
        mEtPhone.text.append(mCurrentItem.objectPhone)
        mEtMobile.text.append(mCurrentItem.mobilePhone)
        mEtKurator.text.append(mCurrentItem.kurator)

        checkBoxState()
    }

    private fun checkBoxState() {

        when (mCurrentItem.order08) {
            "true" -> checkBox08.isChecked = true
        }
        when (mCurrentItem.order15) {
            "true" -> checkBox15.isChecked = true
        }
        when (mCurrentItem.order21) {
            "true" -> checkBox21.isChecked = true
        }
        when (mCurrentItem.order00) {
            "true" -> checkBox00.isChecked = true
        }
        when (mCurrentItem.order02) {
            "true" -> checkBox02.isChecked = true
        }
        when (mCurrentItem.order04) {
            "true" -> checkBox04.isChecked = true
        }
        when (mCurrentItem.order06) {
            "true" -> checkBox06.isChecked = true
        }

    }

    private fun getNewItem(): Items {
        ITEM = mCurrentItem

        val name = mEtName.text.toString().trim()
        val address = mEtAddress.text.toString().trim()
        val phone = mEtPhone.text.toString().trim()
        val mobile = mEtMobile.text.toString().trim()
        val kurator = mEtKurator.text.toString().trim()

        when {
            name.isNotEmpty() -> ITEM.objectName = name
        }

        when {
            address.isNotEmpty() -> ITEM.address = address
        }
        when {
            phone.isNotEmpty() -> ITEM.objectPhone = phone
        }
        when {
            mobile.isNotEmpty() -> ITEM.mobilePhone = mobile
        }
        when {
            kurator.isNotEmpty() -> ITEM.kurator = kurator
        }

        when {
            checkBox08.isChecked -> ITEM.order08 = "true"
            !checkBox08.isChecked -> ITEM.order08 = "false"
        }
        when {
            checkBox15.isChecked -> ITEM.order15 = "true"
            !checkBox15.isChecked -> ITEM.order15 = "false"
        }
        when {
            checkBox21.isChecked -> ITEM.order21 = "true"
            !checkBox21.isChecked -> ITEM.order21 = "false"
        }
        when {
            checkBox00.isChecked -> ITEM.order00 = "true"
            !checkBox00.isChecked -> ITEM.order00 = "false"
        }
        when {
            checkBox02.isChecked -> ITEM.order02 = "true"
            !checkBox02.isChecked -> ITEM.order02 = "false"
        }
        when {
            checkBox04.isChecked -> ITEM.order04 = "true"
            !checkBox04.isChecked -> ITEM.order04 = "false"
        }
        when {
            checkBox06.isChecked -> ITEM.order06 = "true"
            !checkBox06.isChecked -> ITEM.order06 = "false"
        }
        return ITEM
    }

    private fun updateItem(item: Items) {
        val id = item.item_id
        Log.d(TAG, "newName: + ${item.objectName}  ")

        CoroutineScope(Dispatchers.IO).launch {
            try {
                collectionITEMS_REF.document(id)
                    .set(item, SetOptions.merge()).await()
                REPOSITORY_ROOM.deleteMainItem(id)
                REPOSITORY_ROOM.insertMainItem(item)

                withContext(Dispatchers.Main) {
                    APP_ACTIVITY.navController
                        .navigate(R.id.action_updateItemFragment_to_viewPagerFragment)
                    showToast("Данные по объекту ${item.objectName} изменены")
                }
            }catch (e: Exception){
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