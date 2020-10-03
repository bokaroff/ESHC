package com.example.eshc

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.eshc.databinding.ActivityMainBinding
import com.example.eshc.utilits.APP_ACTIVITY

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    val mBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        APP_ACTIVITY = this

        //supportActionBar?.hide()

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        mBinding.bottomNavigation.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id){
                R.id.fragmentStaff, R.id.fragmentStaffLate -> {
                    mBinding.bottomNavigation.visibility = View.GONE
                    mBinding.fab.visibility = View.GONE
                } else ->{
                mBinding.bottomNavigation.visibility = View.VISIBLE
                mBinding.fab.visibility = View.VISIBLE
                }
            }
        }






    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}