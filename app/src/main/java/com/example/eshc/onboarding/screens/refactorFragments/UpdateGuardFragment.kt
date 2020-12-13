package com.example.eshc.onboarding.screens.refactorFragments

import android.graphics.Color
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
import com.example.eshc.databinding.FragmentUpdateGaurdBinding
import com.example.eshc.model.Guards
import com.example.eshc.utilits.*
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class UpdateGuardFragment : Fragment() {

    private var _binding: FragmentUpdateGaurdBinding ? = null
    private val mBinding get() = _binding!!
    private lateinit var mToolbar: Toolbar
    private lateinit var mCurrentItem: Guards
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
        _binding = FragmentUpdateGaurdBinding.inflate(layoutInflater,
            container,false)
        mCurrentItem = arguments?.getSerializable("guard") as Guards
        return mBinding.root
    }
    override fun onStart() {
        super.onStart()
        initialization()
        setEditTexts()

        mButtonSave.setOnClickListener {
            val map =  getNewGuardMap()
            updateGuard(map)
            showToast("Данные изменены")
            APP_ACTIVITY.navController
                .navigate(R.id.action_updateGuardFragment_to_viewPagerFragment)
        }
    }

    private fun initialization() {
        mToolbar = mBinding.fragmentUpdateGuardToolbar
        mToolbar.setupWithNavController(findNavController())
        mTextViewName = mBinding.fragmentUpdateGuardNameTextView
        mEdtxtName = mBinding.fragmentUpdateGuardName
        mEdtxtAddress = mBinding.fragmentUpdateGuardAddress
        mEdtxtPhone = mBinding.fragmentUpdateGuardPhone
        mEdtxtMobile = mBinding.fragmentUpdateGuardMobilePhone
        mEdtxtKurator = mBinding.fragmentUpdateGuardKurator
        mButtonSave = mBinding.fragmentUpdateGuardButtonSave
    }

    private fun setEditTexts() {
        val name = mCurrentItem.guardName
        val address = mCurrentItem.guardWorkPlace
        val phone = mCurrentItem.guardPhone
        val mobile = mCurrentItem.guardPhone_2
        val kurator = mCurrentItem.guardKurator

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


    private fun getNewGuardMap(): Map<String, Any> {
        val name = mEdtxtName.text.toString().trim()
        val address = mEdtxtAddress.text.toString().trim()
        val phone = mEdtxtPhone.text.toString().trim()
        val mobile = mEdtxtMobile.text.toString().trim()
        val kurator = mEdtxtKurator.text.toString().trim()
        val map = mutableMapOf<String, Any>()
        if (name.isNotEmpty()){
            map[guard_name] = name
        }
        if (address.isNotEmpty()){
            map[guard_workPlace] = address
        }
        if (phone.isNotEmpty()){
            map[guard_phone] = phone
        }
        if (mobile.isNotEmpty()){
            map[guard_phone_2] = mobile
        }
        if (kurator.isNotEmpty()){
            map[guard_kurator] = kurator
        }
            return map
    }

  private fun updateGuard(newGuardMap: Map<String, Any>) {
       val id = mCurrentItem.guardFire_id

        CoroutineScope(Dispatchers.IO).launch {
           try {
               collectionGUARDS_REF.document(id)
                   .set(newGuardMap, SetOptions.merge()).await()
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