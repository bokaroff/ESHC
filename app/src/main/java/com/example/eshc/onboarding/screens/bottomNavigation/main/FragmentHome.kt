package com.example.eshc.onboarding.screens.bottomNavigation.main

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.eshc.R
import com.example.eshc.databinding.FragmentHomeBinding
import com.example.eshc.utilits.APP_ACTIVITY
import com.example.eshc.utilits.AUTH
import com.example.eshc.utilits.showToast
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FragmentHome : BottomSheetDialogFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val mBinding get() = _binding!!
    private var fileUri: Uri? = null
    private lateinit var img_profile: ShapeableImageView
    private lateinit var img_camera: ShapeableImageView
    private lateinit var etEmail: EditText
    private lateinit var etUserName: EditText
    private lateinit var btnSave: Button
    private lateinit var txtSignOut: TextView
    private lateinit var mContainer: ConstraintLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialise()
        setUserInfo()
        btnClicks()
    }

    private fun initialise() {
        img_profile = mBinding.ivProfileImage
        img_camera = mBinding.imgCamera
        etUserName = mBinding.etProfileUsername
        etEmail = mBinding.etProfileEmail
        btnSave = mBinding.btnProfileSaveInfo
        txtSignOut = mBinding.tvProfileSignOut
        mContainer = mBinding.fragmentHomeMainContainer
    }

    private fun btnClicks(){
        txtSignOut.setOnClickListener {
            signOutUser()
        }

        btnSave.setOnClickListener {
            saveUserInfo()
        }

        img_profile.setOnClickListener {
            selectImage()
        }

        img_camera.setOnClickListener {
            selectImage()
        }
    }

    private fun setUserInfo(){
        etEmail.setText(AUTH.currentUser?.email)
        etUserName.setText(AUTH.currentUser?.displayName)
        img_profile.setImageURI(AUTH.currentUser?.photoUrl)

        fileUri = AUTH.currentUser?.photoUrl
    }

    private fun  saveUserInfo(){
        AUTH.currentUser?.let{
            val userName = etUserName.text.toString()
            val userEmail = etEmail.text.toString()
            val userProfilePicture = fileUri

            val update = UserProfileChangeRequest.Builder()
                .setDisplayName(userName)
                .setPhotoUri(userProfilePicture)
                .build()

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    it.updateProfile(update).await()
                    it.updateEmail(userEmail).await()

                    withContext(Dispatchers.Main){
                        setUserInfo()
                        showToast("Вы успешно изменили данные аккаунта!")
                    }
                }catch (e: Exception){
                    withContext(Dispatchers.Main){
                        e.message?.let { showToast(it) }
                    }
                }
            }
        }
    }

    private fun signOutUser(){
        AUTH.signOut()

        APP_ACTIVITY.navController
            .navigate(R.id.action_fragmentHome_to_splashFragment)
        showToast("Вы вышли из своего аккаунта")
    }

    private fun selectImage(){
        ImagePicker.with(this)
            .crop()
            .compress(1024)
            .maxResultSize(1080, 1080)
            .start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(resultCode){
            Activity.RESULT_OK ->{
                fileUri = data?.data
                img_profile.setImageURI(fileUri)
            }
            ImagePicker.RESULT_ERROR ->{
                showToast(ImagePicker.getError(data))
            }
            else -> {
                showToast("Отмена")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}