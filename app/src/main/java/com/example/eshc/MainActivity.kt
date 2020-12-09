package com.example.eshc

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.eshc.database.room.ItemRoomDatabase
import com.example.eshc.database.room.RoomRepository
import com.example.eshc.databinding.ActivityMainBinding
import com.example.eshc.model.Guards
import com.example.eshc.model.Items
import com.example.eshc.utilits.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navigationItemSelectedListener:
            BottomNavigationView.OnNavigationItemSelectedListener
    private lateinit var destinationChangedListener:
            NavController.OnDestinationChangedListener
    private lateinit var bottomNavigationView: BottomNavigationView
    lateinit var navController: NavController

    private var _binding: ActivityMainBinding? = null
    val mBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        APP_ACTIVITY = this
        ITEM = Items()
        GUARD = Guards()
        ITEM_ROOM_DATABASE = ItemRoomDatabase.getInstance(this)
        ITEM_ROOM_DAO = ITEM_ROOM_DATABASE.getItemRoomDao()
        REPOSITORY_ROOM = RoomRepository(ITEM_ROOM_DAO)

        setUpNavController()
    }

    private fun setUpNavController() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        bottomNavigationView = mBinding.bottomNavigation
        bottomNavigationView.setupWithNavController(navController)
        bottomNavigationView.itemIconTintList = null

        navigationItemSelectedListener =
            BottomNavigationView.OnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.fragmentHome -> {
                        navController.navigate(R.id.action_viewPagerFragment_to_fragmentHome)
                        bottomNavigationView.visibility = View.GONE
                    }
                    R.id.fragmentView -> {
                        navController.navigate(R.id.action_viewPagerFragment_to_fragmentView)
                        bottomNavigationView.visibility = View.GONE
                    }
                    R.id.fragmentGuard -> {
                        navController.navigate(R.id.action_viewPagerFragment_to_fragmentGuard)
                        bottomNavigationView.visibility = View.GONE
                    }
                    R.id.fragmentGuardLate -> {
                        navController.navigate(R.id.action_viewPagerFragment_to_fragmentGuardLate)
                        bottomNavigationView.visibility = View.GONE
                    }
                    R.id.fragmentItemRoom -> {
                        navController.navigate(R.id.action_viewPagerFragment_to_fragmentItemRoom)
                        bottomNavigationView.visibility = View.GONE
                    }
                }
                true
            }

        destinationChangedListener =
            NavController.OnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.viewPagerFragment -> {
                        bottomNavigationView.visibility = View.VISIBLE
                    }
                    R.id.splashFragment -> {
                        bottomNavigationView.visibility = View.GONE
                    }
                }
            }
    }

    override fun onResume() {
        super.onResume()
        bottomNavigationView
            .setOnNavigationItemSelectedListener(navigationItemSelectedListener)
        navController.addOnDestinationChangedListener(destinationChangedListener)
    }

    override fun onPause() {
        super.onPause()
        bottomNavigationView
            .setOnNavigationItemSelectedListener(null)
        navController.removeOnDestinationChangedListener(destinationChangedListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}