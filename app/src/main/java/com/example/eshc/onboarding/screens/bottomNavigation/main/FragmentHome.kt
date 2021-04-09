package com.example.eshc.onboarding.screens.bottomNavigation.main

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.content.res.AppCompatResources
import com.example.eshc.R
import com.example.eshc.databinding.FragmentHomeCustomBinding
import com.example.eshc.utilits.*
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FragmentHome : BottomSheetDialogFragment() {

    private var _binding: FragmentHomeCustomBinding? = null
    private val mBinding get() = _binding!!
    private var fileUri: Uri? = null
    private lateinit var img_profile: ShapeableImageView
    private lateinit var img_camera: ShapeableImageView
    private lateinit var etEmail: EditText
    private lateinit var etUserName: EditText
    private lateinit var btnSave: Button
    private lateinit var txtSignOut: TextView
    private lateinit var mContainer: LinearLayout
    private lateinit var mSnack: Snackbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.MyBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeCustomBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContainer = mBinding.fragmentHomeCustomMainContainer
        mContainer.background = AppCompatResources.getDrawable(APP_ACTIVITY, R.color.deepBlueStrong)
    }

    override fun onStart() {
        super.onStart()
        checkNetWorkConnection()
        initialise()
        setUserInfo()
        btnClicks()
    }

    private fun checkNetWorkConnection() {
        mSnack = Snackbar
            .make(mBinding.coordinatorLayout, "Проверьте наличие интернета", Snackbar.LENGTH_INDEFINITE)

        val vvv: View = mSnack.view
        val txt = vvv.findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView
        txt.textAlignment = View.TEXT_ALIGNMENT_CENTER

        connectionLiveData.checkValidNetworks()
        connectionLiveData.observe(this, { isNetWorkAvailable ->
            when (isNetWorkAvailable) {
                false -> mSnack.show()
                true -> mSnack.dismiss()
            }
        })
    }

    private fun initialise() {
        img_profile = mBinding.ivProfileImage
        img_camera = mBinding.imgCamera
        etUserName = mBinding.etProfileUsername
        etEmail = mBinding.etProfileEmail
        btnSave = mBinding.btnProfileSaveInfo
        txtSignOut = mBinding.tvProfileSignOut
    }

    private fun btnClicks() {
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

    private fun setUserInfo() {
        etEmail.setText(AUTH.currentUser?.email)
        etUserName.setText(AUTH.currentUser?.displayName)
        img_profile.setImageURI(AUTH.currentUser?.photoUrl)

        fileUri = AUTH.currentUser?.photoUrl
    }

    private fun saveUserInfo() {
        AUTH.currentUser?.let {
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

                    withContext(Dispatchers.Main) {
                        setUserInfo()
                        showToast("Вы успешно изменили данные аккаунта!")
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        e.message?.let { showToast(it) }
                    }
                }
            }
        }
    }

    private fun signOutUser() {
        AUTH.signOut()

        APP_ACTIVITY.navController
            .navigate(R.id.action_fragmentHome_to_splashFragment)
        showToast("Вы вышли из своего аккаунта")
    }

    private fun selectImage() {
        ImagePicker.with(this)
            .crop()
            .compress(1024)
            .maxResultSize(1080, 1080)
            .start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (resultCode) {
            Activity.RESULT_OK -> {
                fileUri = data?.data
                img_profile.setImageURI(fileUri)
            }
            ImagePicker.RESULT_ERROR -> {
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