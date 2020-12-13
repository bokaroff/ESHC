package com.example.eshc.onboarding.screens.refactorFragments

import android.app.Activity
import android.graphics.Color
import android.hardware.input.InputManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
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

class UpdateItemFragment : Fragment() {

    private var _binding: FragmentUpdateItemBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var mToolbar: Toolbar
    private lateinit var mCurentitem: Items
    private lateinit var mTextViewName: TextView
    private lateinit var mEdtxtName: EditText
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
        _binding = FragmentUpdateItemBinding.inflate(layoutInflater,
            container,false)
        mCurentitem = arguments?.getSerializable("item") as Items
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialization()
        setEditTexts()
        mButtonSave.setOnClickListener {
            val map =  getNewItemMap()
            updateItem(map)
            showToast("Данные по объекту изменены")
            APP_ACTIVITY.navController
                .navigate(R.id.action_updateItemFragment_to_viewPagerFragment)
           UIUtil.hideKeyboard(context as Activity)
        }
    }


    private fun initialization() {
        mToolbar = mBinding.fragmentUpdateItemToolbar
        mToolbar.setupWithNavController(findNavController())
        mTextViewName = mBinding.fragmentUpdateItemTextView
        mEdtxtName = mBinding.fragmentUpdateItemName
        mEdtxtAddress = mBinding.fragmentUpdateItemAddress
        mEdtxtPhone = mBinding.fragmentUpdateItemObjectPhone
        mEdtxtMobile = mBinding.fragmentUpdateItemMobilePhone
        mEdtxtKurator = mBinding.fragmentUpdateItemKurator
        mButtonSave = mBinding.fragmentUpdateNewItemButton
    }

    private fun setEditTexts() {
        val name = mCurentitem.objectName
        val address = mCurentitem.address
        val phone = mCurentitem.objectPhone
        val mobile = mCurentitem.mobilePhone
        val kurator = mCurentitem.kurator

        mTextViewName.text = name
        mEdtxtName.hint = name
        if (address.isNotEmpty()) {
            mEdtxtAddress.hint = address
            mEdtxtAddress.setHintTextColor(Color.BLACK)
        }
        if (phone.isNotEmpty()) {
            mEdtxtPhone.hint = phone
            mEdtxtPhone.setHintTextColor(Color.BLACK)
        }
        if (mobile.isNotEmpty()) {
            mEdtxtMobile.hint = mobile
            mEdtxtMobile.setHintTextColor(Color.BLACK)
        }
        if (kurator.isNotEmpty()) {
            mEdtxtKurator.hint = kurator
            mEdtxtKurator.setHintTextColor(Color.BLACK)
        }
    }

    private fun getNewItemMap(): Map<String, Any> {
        val name = mEdtxtName.text.toString().trim()
        val address = mEdtxtAddress.text.toString().trim()
        val phone = mEdtxtPhone.text.toString().trim()
        val mobile = mEdtxtMobile.text.toString().trim()
        val kurator = mEdtxtKurator.text.toString().trim()
        val map = mutableMapOf<String, Any>()
        if (name.isNotEmpty()) {
            map[item_name] = name
        }
        if (address.isNotEmpty()) {
            map[item_address] = address
        }
        if (phone.isNotEmpty()) {
            map[item_phone] = phone
        }
        if (mobile.isNotEmpty()){
            map[item_mobilePhone] = mobile
        }
        if (kurator.isNotEmpty()){
            map[item_kurator] = kurator
        }
        return map
    }

    private fun updateItem(newItemMap: Map<String, Any>) {
        val id = mCurentitem.item_id

        CoroutineScope(Dispatchers.IO).launch {
            try {
                collectionITEMS_REF.document(id)
                    .set(newItemMap, SetOptions.merge()).await()
            }catch (e: Exception){
                withContext(Dispatchers.Main) {
                    showToast(e.message.toString())
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}