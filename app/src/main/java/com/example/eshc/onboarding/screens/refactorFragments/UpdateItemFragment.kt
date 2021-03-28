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
import android.widget.TextView
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
    private var currentTime: Date = Date()

    private lateinit var mToolbar: Toolbar
    private lateinit var mTextView: TextView
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
        initialise()
        checkTimeRanges()
        mButtonSave.setOnClickListener {

            if (timeRange08 || timeRange15 || timeRange21 || timeRangeBeforeMidnight || timeRangeAfterMidnight
                || timeRange02 || timeRange04 || timeRange06
            ) {
                showToast("Внесение изменений во время доклада невозможно!")
                return@setOnClickListener
            }

            val item = getNewItem()
            if (item.objectName.isNotEmpty()) {
                updateItem(item)
            }
            UIUtil.hideKeyboard(context as Activity)
        }
    }

    private fun checkTimeRanges() {
        currentTime = Calendar.getInstance(Locale.getDefault()).time

        timeRange08 = (currentTime.after(timeStart08.time)) && (currentTime.before(timeEnd08.time))
        timeRange15 = (currentTime.after(timeStart15.time)) && (currentTime.before(timeEnd15.time))
        timeRange21 = (currentTime.after(timeStart21.time)) && (currentTime.before(timeEnd21.time))
        timeRange02 = (currentTime.after(timeStart02.time)) && (currentTime.before(timeEnd02.time))
        timeRange04 = (currentTime.after(timeStart04.time)) && (currentTime.before(timeEnd04.time))
        timeRange06 = (currentTime.after(timeStart06.time)) && (currentTime.before(timeEnd06.time))
        timeRangeBeforeMidnight = (currentTime.after(timeStartBeforeMidnight.time))
                && (currentTime.before(timeEndBeforeMidnight.time))

        timeRangeAfterMidnight = (currentTime.after(timeStartAfterMidnight.time))
                && (currentTime.before(timeEndAfterMidnight.time))
    }

    private fun initialise() {
        mToolbar = mBinding.fragmentUpdateItemToolbar
        mTextView = mBinding.fragmentUpdateItemTextView
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

        mTextView.text = mCurrentItem.objectName
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


/*

        when {
            mCurrentItem.order08 == "true" -> checkBox08.isChecked = true

            mCurrentItem.order15 == "true" -> checkBox15.isChecked = true

            mCurrentItem.order21 == "true" -> checkBox21.isChecked = true

            mCurrentItem.order00 == "true" -> checkBox00.isChecked = true

            mCurrentItem.order02 == "true" -> checkBox02.isChecked = true

            mCurrentItem.order04 == "true" -> checkBox04.isChecked = true

            mCurrentItem.order06 == "true" -> checkBox06.isChecked = true
        }

 */
    }

    private fun getNewItem(): Items {
        ITEM = mCurrentItem

        val name = mEtName.text.toString().trim()
        val address = mEtAddress.text.toString().trim()
        val phone = mEtPhone.text.toString().trim()
        val mobile = mEtMobile.text.toString().trim()
        val kurator = mEtKurator.text.toString().trim()

        when {
            name.isEmpty() -> {
                ITEM.objectName = ""
                showToast("Введите имя объекта")
            }
            else -> ITEM.objectName = name
        }

        ITEM.address = address
        ITEM.objectPhone = phone
        ITEM.mobilePhone = mobile
        ITEM.kurator = kurator

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


/*
        when {
            checkBox08.isChecked -> ITEM.order08 = "true"
            !checkBox08.isChecked -> ITEM.order08 = "false"

            checkBox15.isChecked -> ITEM.order15 = "true"
            !checkBox15.isChecked -> ITEM.order15 = "false"

            checkBox21.isChecked -> ITEM.order21 = "true"
            !checkBox21.isChecked -> ITEM.order21 = "false"

            checkBox00.isChecked -> ITEM.order00 = "true"
            !checkBox00.isChecked -> ITEM.order00 = "false"

            checkBox02.isChecked -> ITEM.order02 = "true"
            !checkBox02.isChecked -> ITEM.order02 = "false"

            checkBox04.isChecked -> ITEM.order04 = "true"
            !checkBox04.isChecked -> ITEM.order04 = "false"

            checkBox06.isChecked -> ITEM.order06 = "true"
            !checkBox06.isChecked -> ITEM.order06 = "false"
        }

 */

        ITEM.state = stateMain

        return ITEM
    }

    private fun updateItem(item: Items) {
        val newId = item.item_id
        val newName = item.objectName.toLowerCase(Locale.ROOT).trim()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val roomList = REPOSITORY_ROOM.getMainItemList()

                for (doc in roomList) {
                    val oldName = doc.objectName.toLowerCase(Locale.ROOT).trim()
                    val oldId = doc.item_id

                    if (oldName == newName && oldId != newId) {
                        Log.d(TAG, "equal: + $oldName + $newName ")
                        withContext(Dispatchers.Main) {
                            showToast(" Объект с таким именем уже существует")
                        }
                        return@launch
                    }
                }
                collectionITEMS_REF.document(newId)
                    .set(item, SetOptions.merge()).await()
                REPOSITORY_ROOM.deleteMainItem(newId)

                Log.d(TAG, "job_1.isCompleted")

                REPOSITORY_ROOM.insertItem(item)
                withContext(Dispatchers.Main) {
                    APP_ACTIVITY.navController
                        .navigate(R.id.action_updateItemFragment_to_viewPagerFragment)
                    showToast("Данные по объекту ${item.objectName} изменены")
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