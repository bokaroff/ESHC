package com.example.eshc

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
import androidx.navigation.fragment.findNavController
import com.example.eshc.databinding.FragmentSplashBinding
import com.example.eshc.utilits.AUTH
import com.example.eshc.utilits.showToast
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class SplashFragment : androidx.fragment.app.Fragment() {
    private var _binding: FragmentSplashBinding? = null
    private val mBinding get() = _binding!!
    private var firstTimeUser = true
    private var fileUri: Uri? = null

    private lateinit var img: ShapeableImageView
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var txtRegister: TextView
    private lateinit var btnLogIn: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        checkIfUserIsLoggedIn()
        initialise()
        buttonClicks()
    }

    private fun initialise() {
        img = mBinding.ivProfileImage
        etEmail = mBinding.etEmailLogin
        etPassword = mBinding.etPasswordLogin
        txtRegister = mBinding.txtRegister
        btnLogIn = mBinding.btnLogin
    }

    private fun buttonClicks() {
        btnLogIn.setOnClickListener {
            firstTimeUser = false
            createOrLoginUser()
        }

        txtRegister.setOnClickListener {
            firstTimeUser = true
            createOrLoginUser()
        }

        img.setOnClickListener {
            selectImage()
        }
    }

    private fun createOrLoginUser() {
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    if (firstTimeUser) {
                        AUTH.createUserWithEmailAndPassword(email, password).await()
                        AUTH.currentUser.let {
                            val update = UserProfileChangeRequest.Builder()
                                .setPhotoUri(fileUri)
                                .build()
                            it?.updateProfile(update)
                        }?.await()
                    } else {
                        AUTH.signInWithEmailAndPassword(email, password).await()
                    }

                    withContext(Dispatchers.Main) {
                        val name = AUTH.currentUser?.displayName
                        if (name.isNullOrEmpty()) {
                            showToast("Добро пожаловать!")
                            findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)

                        } else {
                            showToast("Добро пожаловать $name!")
                            findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        e.message?.let { showToast(it) }
                    }
                }
            }
        } else {
            showToast("Заполните все поля")
        }
    }

    private fun checkIfUserIsLoggedIn() {
        if (AUTH.currentUser != null) {
            findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
        }
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
                img.setImageURI(fileUri)
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