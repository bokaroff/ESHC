package com.example.eshc

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.eshc.databinding.FragmentSplashBinding
import com.example.eshc.utilits.TAG
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import de.hdodenhof.circleimageview.CircleImageView

class SplashFragment : Fragment() {
    private var _binding: FragmentSplashBinding? = null
    private val mBinding get() = _binding!!

    private lateinit var img: CircleImageView
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnSingUp: Button
    private lateinit var btnLogIn: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashBinding.inflate(layoutInflater,container,false)


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
        initialise()
        btnLogIn.setOnClickListener {
            findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
        }
    }

    private fun initialise() {
        img = mBinding.ivProfileImage
        etEmail = mBinding.etEmailLogin
        etPassword = mBinding.etPasswordLogin
        btnSingUp = mBinding.btnRegister
        btnLogIn = mBinding.btnLogin
    }


}