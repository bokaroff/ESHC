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
    private lateinit var mEdtxtField08: EditText
    private lateinit var mEdtxtField15: EditText
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
        mButtonSave.setOnClickListener {
            val map =  getNewItem()
            updateItem(ITEM)
            showToast("Данные по объекту ${ITEM.objectName} изменены")
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
        mEdtxtField08 = mBinding.fragmentUpdateItemField08
        mEdtxtField15 = mBinding.fragmentUpdateItemField15
        mButtonSave = mBinding.fragmentUpdateNewItemButton
        mTextViewName.text = mCurentitem.objectName
    }

    private fun getNewItem(): Items {

        ITEM = mCurentitem

        val name = mEdtxtName.text.toString().trim()
        val address = mEdtxtAddress.text.toString().trim()
        val phone = mEdtxtPhone.text.toString().trim()
        val mobile = mEdtxtMobile.text.toString().trim()
        val kurator = mEdtxtKurator.text.toString().trim()
        val field08 = mEdtxtField08.text.toString().trim()
        val field15 = mEdtxtField15.text.toString().trim()

        if (name.isNotEmpty()) {
           ITEM.objectName = name
        }
        if (address.isNotEmpty()) {
            ITEM.address = address
        }
        if (phone.isNotEmpty()) {
            ITEM.objectPhone = phone
        }
        if (mobile.isNotEmpty()){
            ITEM.mobilePhone = mobile
        }
        if (kurator.isNotEmpty()){
            ITEM.kurator = kurator
        }
        if (field08.isNotEmpty()){
            ITEM.order08 = field08
        }
        if (field15.isNotEmpty()){
            ITEM.order15 = field15
        }
        return ITEM
    }

    private fun updateItem(item: Items) {
        val id = mCurentitem.item_id

        CoroutineScope(Dispatchers.IO).launch {
            try {
                collectionITEMS_REF.document(id)
                    .set(item, SetOptions.merge()).await()
                    REPOSITORY_ROOM.deleteMainItem(id)
                    REPOSITORY_ROOM.insertMainItem(item)
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