package com.example.eshc

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.eshc.databinding.ActivityMainBinding
import com.example.eshc.model.Items
import com.example.eshc.utilits.APP_ACTIVITY
import com.example.eshc.utilits.ITEMS

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    val mBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        APP_ACTIVITY = this
        ITEMS = Items()
       // supportActionBar?.hide()
        setUpNavController()
    }




    private fun setUpNavController() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        mBinding.bottomNavigation.setupWithNavController(navController)
        mBinding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.fragmentHome -> {
                    navController.navigate(R.id.action_viewPagerFragment_to_fragmentHome)
                    mBinding.bottomNavigation.visibility = View.GONE
                    mBinding.fab.visibility = View.GONE
                }
                R.id.fragmentView -> {
                    navController.navigate(R.id.action_viewPagerFragment_to_fragmentView)
                    mBinding.bottomNavigation.visibility = View.GONE
                    mBinding.fab.visibility = View.GONE
                }
                R.id.fragmentStaff -> {
                    navController.navigate(R.id.action_viewPagerFragment_to_fragmentStaff)
                    mBinding.bottomNavigation.visibility = View.GONE
                    mBinding.fab.visibility = View.GONE
                }
                R.id.fragmentStaffLate -> {
                    navController.navigate(R.id.action_viewPagerFragment_to_fragmentStaffLate)
                    mBinding.bottomNavigation.visibility = View.GONE
                    mBinding.fab.visibility = View.GONE
                }else ->{
                mBinding.bottomNavigation.visibility = View.VISIBLE
                mBinding.fab.visibility = View.VISIBLE
            }
            }
            true
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id){
                R.id.viewPagerFragment -> {
                    mBinding.bottomNavigation.visibility = View.VISIBLE
                    mBinding.fab.visibility = View.VISIBLE
                }
                R.id.splashFragment -> {
                    mBinding.bottomNavigation.visibility = View.GONE
                    mBinding.fab.visibility = View.GONE
                }

            }
        }
    }




    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}