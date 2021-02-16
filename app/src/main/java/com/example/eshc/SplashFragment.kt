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
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.eshc.databinding.ActivityMainBinding
import com.example.eshc.databinding.FragmentSplashBinding
import com.example.eshc.utilits.APP_ACTIVITY
import com.example.eshc.utilits.AUTH
import com.example.eshc.utilits.showToast
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.UserProfileChangeRequest
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_splash.*
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class SplashFragment : Fragment() {
    private var _binding: FragmentSplashBinding? = null
    private val mBinding get() = _binding!!
    private var firstTimeUser = true
    private var fileUri: Uri? = null

    private lateinit var img: CircleImageView
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnSingUp: Button
    private lateinit var btnLogIn: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashBinding.inflate(layoutInflater, container, false)


        /*
        Handler().postDelayed({
            if (view != null) {

                findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
            }
        }, 1000)
        Log.d(TAG, "classname: $javaClass")

         */

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
        btnSingUp = mBinding.btnRegister
        btnLogIn = mBinding.btnLogin
    }

    private fun buttonClicks() {
        btnLogIn.setOnClickListener {
            //  findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
            firstTimeUser = false
            createOrLoginUser()
        }

        btnSingUp.setOnClickListener {
            //  findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
            firstTimeUser = true
            createOrLoginUser()
        }

        iv_profileImage.setOnClickListener {
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
                        showToast("Вы вошли в свой аккаунт")

                        findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
                    }

                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        e.message?.let { showToast(it) }
                    }
                }
            }
        }
    }

    private fun checkIfUserIsLoggedIn(){
        if (AUTH.currentUser != null){
            findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
        }
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
                iv_profileImage.setImageURI(fileUri)
            }
            ImagePicker.RESULT_ERROR ->{
                showToast(ImagePicker.getError(data))
            }
            else -> {
                showToast("Отмена")
            }
        }
    }


}