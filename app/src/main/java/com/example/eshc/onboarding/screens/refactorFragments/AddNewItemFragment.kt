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
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.eshc.R
import com.example.eshc.databinding.FragmentAddNewItemBinding
import com.example.eshc.model.Items
import com.example.eshc.utilits.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil
import java.util.*

class AddNewItemFragment : Fragment() {

    private var timeStartBeforeMidnight: Calendar = Calendar.getInstance(Locale.getDefault())
    private var timeStartAfterMidnight: Calendar = Calendar.getInstance(Locale.getDefault())
    private var timeEndBeforeMidnight: Calendar = Calendar.getInstance(Locale.getDefault())
    private var timeEndAfterMidnight: Calendar = Calendar.getInstance(Locale.getDefault())
    private var timeRangeBeforeMidnight: Boolean = false
    private var timeRangeAfterMidnight: Boolean = false

    private var timeStart02: Calendar = Calendar.getInstance(Locale.getDefault())
    private var timeEnd02: Calendar = Calendar.getInstance(Locale.getDefault())
    private var timeRange02: Boolean = false

    private var timeStart04: Calendar = Calendar.getInstance(Locale.getDefault())
    private var timeEnd04: Calendar = Calendar.getInstance(Locale.getDefault())
    private var timeRange04: Boolean = false

    private var timeStart06: Calendar = Calendar.getInstance(Locale.getDefault())
    private var timeEnd06: Calendar = Calendar.getInstance(Locale.getDefault())
    private var timeRange06: Boolean = false

    private var timeStart08: Calendar = Calendar.getInstance(Locale.getDefault())
    private var timeEnd08: Calendar = Calendar.getInstance(Locale.getDefault())
    private var timeRange08: Boolean = false

    private var timeStart15: Calendar = Calendar.getInstance(Locale.getDefault())
    private var timeEnd15: Calendar = Calendar.getInstance(Locale.getDefault())
    private var timeRange15: Boolean = false

    private var timeStart21: Calendar = Calendar.getInstance(Locale.getDefault())
    private var timeEnd21: Calendar = Calendar.getInstance(Locale.getDefault())
    private var timeRange21: Boolean = false

    private var _binding: FragmentAddNewItemBinding? = null
    private val mBinding get() = _binding!!
    private var currentTime: Date = Date()

    private lateinit var mToolbar: Toolbar
    private lateinit var mEdtxtObjectName: EditText
    private lateinit var mEdtxtAddress: EditText
    private lateinit var mEdtxtPhone: EditText
    private lateinit var mEdtxtMobile: EditText
    private lateinit var mEdtxtKurator: EditText
    private lateinit var checkBox08: CheckBox
    private lateinit var checkBox15: CheckBox
    private lateinit var checkBox21: CheckBox
    private lateinit var checkBox00: CheckBox
    private lateinit var checkBox02: CheckBox
    private lateinit var checkBox04: CheckBox
    private lateinit var checkBox06: CheckBox
    private lateinit var mButtonSave: Button
    private lateinit var mSnack: Snackbar
    private lateinit var mContainer: ConstraintLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddNewItemBinding.inflate(
            layoutInflater,
            container, false
        )
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialise()
        setCurrentTime()
        checkTimeRanges()
        saveChanges()
    }

    override fun onStop() {
        super.onStop()
        mSnack.dismiss()
    }

    private fun initialise() {
        mToolbar = mBinding.fragmentAddNewItemToolbar
        mToolbar.setupWithNavController(findNavController())
        mEdtxtObjectName = mBinding.fragmentAddNewItemName
        mEdtxtAddress = mBinding.fragmentAddNewItemAddress
        mEdtxtPhone = mBinding.fragmentAddNewItemPhone
        mEdtxtMobile = mBinding.fragmentAddNewItemMobilePhone
        mEdtxtKurator = mBinding.fragmentAddNewItemKurator
        checkBox08 = mBinding.fragmentAddItemField08
        checkBox15 = mBinding.fragmentAddItemField15
        checkBox21 = mBinding.fragmentAddItemField21
        checkBox00 = mBinding.fragmentAddItemField00
        checkBox02 = mBinding.fragmentAddItemField02
        checkBox04 = mBinding.fragmentAddItemField04
        checkBox06 = mBinding.fragmentAddItemField06
        mButtonSave = mBinding.fragmentAddNewItemButtonAdd
        mContainer = mBinding.fragmentAddNewItemContainer

        mSnack =
            Snackbar
                .make(
                    mContainer,
                    "Внесение изменений во время доклада невозможно!",
                    Snackbar.LENGTH_INDEFINITE
                )

        val view: View = mSnack.view
        val txt =
            view.findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView
        txt.textAlignment = View.TEXT_ALIGNMENT_CENTER
    }

    private fun setCurrentTime() {
        timeStartBeforeMidnight.set(Calendar.HOUR_OF_DAY, 23)
        timeStartBeforeMidnight.set(Calendar.MINUTE, 40)
        timeStartBeforeMidnight.set(Calendar.SECOND, 0)
        timeEndBeforeMidnight.set(Calendar.HOUR_OF_DAY, 23)
        timeEndBeforeMidnight.set(Calendar.MINUTE, 59)
        timeEndBeforeMidnight.set(Calendar.SECOND, 59)

        timeStartAfterMidnight.set(Calendar.HOUR_OF_DAY, 0)
        timeStartAfterMidnight.set(Calendar.MINUTE, 0)
        timeStartAfterMidnight.set(Calendar.SECOND, 0)
        timeEndAfterMidnight.set(Calendar.HOUR_OF_DAY, 0)
        timeEndAfterMidnight.set(Calendar.MINUTE, 30)
        timeEndAfterMidnight.set(Calendar.SECOND, 0)

        timeStart02.set(Calendar.HOUR_OF_DAY, 1)
        timeStart02.set(Calendar.MINUTE, 40)
        timeStart02.set(Calendar.SECOND, 0)
        timeEnd02.set(Calendar.HOUR_OF_DAY, 2)
        timeEnd02.set(Calendar.MINUTE, 30)
        timeEnd02.set(Calendar.SECOND, 0)

        timeStart04.set(Calendar.HOUR_OF_DAY, 3)
        timeStart04.set(Calendar.MINUTE, 40)
        timeStart04.set(Calendar.SECOND, 0)
        timeEnd04.set(Calendar.HOUR_OF_DAY, 4)
        timeEnd04.set(Calendar.MINUTE, 30)
        timeEnd04.set(Calendar.SECOND, 0)

        timeStart06.set(Calendar.HOUR_OF_DAY, 5)
        timeStart06.set(Calendar.MINUTE, 40)
        timeStart06.set(Calendar.SECOND, 0)
        timeEnd06.set(Calendar.HOUR_OF_DAY, 6)
        timeEnd06.set(Calendar.MINUTE, 30)
        timeEnd06.set(Calendar.SECOND, 0)

        timeStart08.set(Calendar.HOUR_OF_DAY, 7)
        timeStart08.set(Calendar.MINUTE, 0)
        timeStart08.set(Calendar.SECOND, 0)
        timeEnd08.set(Calendar.HOUR_OF_DAY, 11)
        timeEnd08.set(Calendar.MINUTE, 30)
        timeEnd08.set(Calendar.SECOND, 0)

        timeStart15.set(Calendar.HOUR_OF_DAY, 14)
        timeStart15.set(Calendar.MINUTE, 40)
        timeStart15.set(Calendar.SECOND, 0)
        timeEnd15.set(Calendar.HOUR_OF_DAY, 15)
        timeEnd15.set(Calendar.MINUTE, 30)
        timeEnd15.set(Calendar.SECOND, 0)

        timeStart21.set(Calendar.HOUR_OF_DAY, 20)
        timeStart21.set(Calendar.MINUTE, 40)
        timeStart21.set(Calendar.SECOND, 0)
        timeEnd21.set(Calendar.HOUR_OF_DAY, 23)
        timeEnd21.set(Calendar.MINUTE, 0)
        timeEnd21.set(Calendar.SECOND, 0)
    }

    private fun checkTimeRanges() {
        currentTime = Calendar.getInstance(Locale.getDefault()).time

        if ((currentTime.after(timeStart08.time)) && (currentTime.before(timeEnd08.time))) {
            timeRange08 = true
        }

        if ((currentTime.after(timeStart15.time)) && (currentTime.before(timeEnd15.time))) {
            timeRange15 = true
        }

        if ((currentTime.after(timeStart21.time)) && (currentTime.before(timeEnd21.time))) {
            timeRange21 = true
        }

        if ((currentTime.after(timeStart02.time)) && (currentTime.before(timeEnd02.time))) {
            timeRange02 = true
        }

        if ((currentTime.after(timeStart04.time)) && (currentTime.before(timeEnd04.time))) {
            timeRange04 = true
        }

        if ((currentTime.after(timeStart06.time)) && (currentTime.before(timeEnd06.time))) {
            timeRange06 = true
        }

        if ((currentTime.after(timeStartBeforeMidnight.time))
            && (currentTime.before(timeEndBeforeMidnight.time))
        ) {
            timeRangeBeforeMidnight = true
        }

        if ((currentTime.after(timeStartAfterMidnight.time))
            && (currentTime.before(timeEndAfterMidnight.time))
        ) {
            timeRangeAfterMidnight = true
        }
    }

    private fun saveChanges() {
        mButtonSave.setOnClickListener {
            if (timeRange08 || timeRange15 || timeRange21 || timeRangeBeforeMidnight || timeRangeAfterMidnight
                || timeRange02 || timeRange04 || timeRange06
            ) {
                showToast("Внесение изменений во время доклада невозможно!")
                 return@setOnClickListener
            } else {
                val item = getNewItem()
                if (item.objectName.isNotEmpty()) {
                    addNewItem(item)
                }
                UIUtil.hideKeyboard(context as Activity)
            }
        }
    }

    private fun getNewItem(): Items {
        val name = mEdtxtObjectName.text.toString().trim()
        val address = mEdtxtAddress.text.toString().trim()
        val phone = mEdtxtPhone.text.toString().trim()
        val mobile = mEdtxtMobile.text.toString().trim()
        val kurator = mEdtxtKurator.text.toString().trim()

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

        ITEM.state = stateMain

        return ITEM
    }

    private fun addNewItem(item: Items) {
        val newName = item.objectName.toLowerCase(Locale.ROOT).trim()
        Log.d(TAG, "newName: + $newName  ")
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val roomList = REPOSITORY_ROOM.getMainItemList()

                for (doc in roomList) {

                    val oldName = doc.objectName
                        .toLowerCase(Locale.ROOT).trim()

                    if (oldName == newName) {
                        withContext(Dispatchers.Main) {
                            showToast(" Объект с таким именем уже существует")
                        }
                        return@launch
                    }
                }

                val docRef = collectionITEMS_REF.add(item).await()
                val key = docRef.id
                collectionITEMS_REF.document(key)
                    .update(item_fire_id, key, item_worker08, "Создан новый объект").await()
                item.item_id = key
                item.state = stateMain
                REPOSITORY_ROOM.insertItem(item)

                withContext(Dispatchers.Main) {
                    APP_ACTIVITY.navController
                        .navigate(R.id.action_addNewItemFragment_to_viewPagerFragment)
                    showToast("Добавлен новый объект ${ITEM.objectName}")
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