package com.example.eshc.onboarding.screens.refactorFragments

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.eshc.R
import com.example.eshc.databinding.FragmentAddNewItemBinding
import com.example.eshc.databinding.FragmentUpdateItemBinding
import com.example.eshc.utilits.*
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil

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
        getNewItemMap()
        mButtonSave.setOnClickListener {
            val map =  getNewItemMap()
         //   addNewItem(map)
          //  showToast("Добавлен новый объект ${map[item_name]}")
          //  APP_ACTIVITY.navController
            //    .navigate(R.id.action_addNewItemFragment_to_viewPagerFragment)
           // UIUtil.hideKeyboard(context as Activity)
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
        val map = mutableMapOf<String, Any>()

        if (name.isEmpty()) {
           // showToast("Введите имя объекта")
        } else{
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

/*
    private fun addNewItem(newItemMap: Map<String, Any>) {
        val id = collectionITEMS_REF.document().id

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val querySnapshot = collectionITEMS_REF.get().await()
                for (dc in querySnapshot) {
                    val itemName = dc[item_name].toString().trim()



                   // if (itemName == newItemMap[item_name]) {
                   //     Log.d(TAG, "addNewItem: + $itemName + ${newItemMap[item_name]} ")
                   //    withContext(Dispatchers.Main) {
                   //         showToast("Объект с таким именем уже существует")
                   //    }
                  //  }
                }

            } catch (e: Exception){
                withContext(Dispatchers.Main) {
                    showToast(e.message.toString())
                }
            }
        }
    }


 */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}