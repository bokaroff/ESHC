package com.example.eshc

import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {

    private lateinit var mSnack: Snackbar

    private lateinit var navigationItemSelectedListener:
            BottomNavigationView.OnNavigationItemSelectedListener
    private lateinit var destinationChangedListener:
            NavController.OnDestinationChangedListener
    private lateinit var bottomNavigationView: BottomNavigationView
    lateinit var navController: NavController

    private var _binding: ActivityMainBinding? = null
    val mBinding get() = _binding!!

    //  @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        connectionLiveData = ConnectionLiveData(this)

        initialise()
        getMainItemsList()
        getMainGuardsList()
        setUpNavController()
        checkForPermissions(android.Manifest.permission.CALL_PHONE, "звонки", CALL_PHONE_RQ)
    }

    private fun initialise() {
        APP_ACTIVITY = this
        AUTH = FirebaseAuth.getInstance()
        ITEM = Items()
        GUARD = Guards()
        ITEM_ROOM_DATABASE = ItemRoomDatabase.getInstance(this)
        ITEM_ROOM_DAO = ITEM_ROOM_DATABASE.getItemRoomDao()
        REPOSITORY_ROOM = RoomRepository(ITEM_ROOM_DAO)

        mSnack = Snackbar
            .make(mBinding.root, "Проверьте наличие интернета", Snackbar.LENGTH_INDEFINITE)

        val view: View = mSnack.view
        val txt = view.findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView
        txt.textAlignment = View.TEXT_ALIGNMENT_CENTER
    }

    private fun getMainItemsList() {
        val itemsList = mutableListOf<Items>()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val itemsData = REPOSITORY_ROOM.getMainItemList()

                if (itemsData.isEmpty()) {

                    val querySnapshot = collectionITEMS_REF
                        .orderBy("objectName", Query.Direction.ASCENDING)
                        .get().await()

                    for (documentSnapShot in querySnapshot) {
                        val item = documentSnapShot.toObject(Items::class.java)
                        item.item_id = documentSnapShot.id
                        itemsList.add(item)
                    }
                    REPOSITORY_ROOM.insertItemList(itemsList)
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    e.message?.let { showToast(it) }
                }
            }
        }
    }

    private fun getMainGuardsList() {
        val guardsList = mutableListOf<Guards>()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val guardsData = REPOSITORY_ROOM.getMainGuardList()

                if (guardsData.isEmpty()) {

                    val querySnapshot = collectionGUARDS_REF
                        .orderBy("guardName", Query.Direction.ASCENDING).get().await()

                    for (documentSnapShot in querySnapshot) {
                        val guard = documentSnapShot.toObject(Guards::class.java)
                        guard.guardFire_id = documentSnapShot.id
                        guardsList.add(guard)
                    }
                    REPOSITORY_ROOM.insertGuardList(guardsList)
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    e.message?.let { showToast(it) }
                }
            }
        }
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

    private fun checkForPermissions(permission: String, name: String, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when {
                ContextCompat.checkSelfPermission(
                    applicationContext,
                    permission
                ) == PackageManager.PERMISSION_GRANTED -> {
                }
                shouldShowRequestPermissionRationale(permission) -> showDialog(
                    permission,
                    name,
                    requestCode
                )

                else -> ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        fun innerCheck(name: String) {
            if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                showToast("Отказано в разрешении на $name")
            } else {
                showToast("Разрешение на $name получено")
            }
        }

        when (requestCode) {
            CALL_PHONE_RQ -> innerCheck("звонки")
        }
    }

    private fun showDialog(permission: String, name: String, requestCode: Int) {
        val builder = AlertDialog.Builder(this)

        builder.apply {
            setMessage("Необходимо разрешение на $name для использования этого приложения")
            setTitle("Необходимо разрешение!")
            setPositiveButton("Да") { dialogInterface: DialogInterface, i: Int ->
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(permission),
                    requestCode
                )
            }
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun checkNetWorkConnection() {
        connectionLiveData.checkValidNetworks()
        connectionLiveData.observe(this, { isNetWorkAvailable ->
            when (isNetWorkAvailable) {
                false -> mSnack.show()
                true -> mSnack.dismiss()
            }
        })
    }

    override fun onStart() {
        super.onStart()
        checkNetWorkConnection()
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