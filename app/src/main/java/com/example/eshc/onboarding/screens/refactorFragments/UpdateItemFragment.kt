package com.example.eshc.onboarding.screens.refactorFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.eshc.R
import com.example.eshc.databinding.FragmentGuardBinding
import com.example.eshc.databinding.FragmentUpdateItemBinding
import com.example.eshc.model.Guards
import com.example.eshc.model.Items
import com.example.eshc.utilits.*
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

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
        if(mobile.isNotEmpty()){
            mEdtxtMobile.hint = mobile
        }
        if(phone.isNotEmpty()){
            mEdtxtPhone.hint = phone
        }
    }

    private fun getNewItemMap(): Map<String, Any> {
        val name = mBinding.fragmentUpdateItemName.text.toString()
        val address = mBinding.fragmentUpdateItemAddress.text.toString()
        val phone = mBinding.fragmentUpdateItemObjectPhone.text.toString()
        val mobile = mBinding.fragmentUpdateItemMobilePhone.text.toString()
        val kurator = mBinding.fragmentUpdateItemKurator.text.toString()
        val map = mutableMapOf<String, Any>()
        if (name.isNotEmpty()){
            map[item_name] = name
        }
        if (address.isNotEmpty()){
            map[item_address] = address
        }
        if (phone.isNotEmpty()){
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