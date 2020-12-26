package com.example.eshc

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.eshc.databinding.FragmentSplashBinding
import com.example.eshc.utilits.TAG

class SplashFragment : Fragment() {
    private var _binding: FragmentSplashBinding? = null
    private val mBinding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashBinding.inflate(layoutInflater,container,false)

        Handler().postDelayed({
            if (view != null) {

                findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
            }
        }, 1000)
        Log.d(TAG, "classname: $javaClass")

        return mBinding.root
    }
}