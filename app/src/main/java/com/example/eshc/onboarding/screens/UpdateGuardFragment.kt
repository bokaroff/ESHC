package com.example.eshc.onboarding.screens

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.eshc.R
import com.example.eshc.databinding.FragmentUpdateGaurdBinding
import com.example.eshc.databinding.FragmentUpdateItemBinding
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
    private lateinit var mCurentitem: Guards
    private lateinit var mButtonSave: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateGaurdBinding.inflate(layoutInflater,
            container,false)
        mCurentitem = arguments?.getSerializable("guard") as Guards
        return mBinding.root
    }
    override fun onStart() {
        super.onStart()
        initialization()
        mButtonSave.setOnClickListener {
            val map =  getNewGuardMap()
            updateGuard(map)
            showToast("Данные изменены")
            APP_ACTIVITY.navController
                .navigate(R.id.action_updateGuardFragment_to_viewPagerFragment)
        }
    }

    private fun getNewGuardMap(): Map<String, Any> {
        val name = mBinding.fragmentUpdateGuardName.text.toString()
        val address = mBinding.fragmentUpdateGuardAddress.text.toString()
        val phone = mBinding.fragmentUpdateGuardPhone.text.toString()
        val mobile = mBinding.fragmentUpdateGuardMobilePhone.text.toString()
        val kurator = mBinding.fragmentUpdateGuardKurator.text.toString()
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

    private fun initialization() {
        mButtonSave = mBinding.fragmentUpdateGuardButtonSave
        mToolbar = mBinding.fragmentUpdateGuardToolbar
        mToolbar.setupWithNavController(findNavController())
        mBinding.fragmentUpdateGuardName.hint = mCurentitem.guardName
        mBinding.fragmentUpdateGuardAddress.hint = mCurentitem.guardWorkPlace
        mBinding.fragmentUpdateGuardPhone.hint = mCurentitem.guardPhone
        mBinding.fragmentUpdateGuardMobilePhone.hint = mCurentitem.guardPhone_2
        mBinding.fragmentUpdateGuardKurator.hint = mCurentitem.guardKurator
    }

     fun updateGuard(newGuardMap: Map<String, Any>) {
       val id = mCurentitem.guardFire_id

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