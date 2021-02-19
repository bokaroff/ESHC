package com.example.eshc.onboarding.screens.bottomNavigation

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.eshc.R
import com.example.eshc.adapters.AdapterGuardAddNewLate
import com.example.eshc.databinding.FragmentGuardAddNewLateBinding
import com.example.eshc.model.Guards
import com.example.eshc.model.Items
import com.example.eshc.utilits.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class FragmentGuardAddNewLate : Fragment() {

    private var _binding: FragmentGuardAddNewLateBinding? = null
    private val mBinding get() = _binding!!
    private var mList = mutableListOf<Guards>()

    private lateinit var mToolbar: Toolbar
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mSearchView: SearchView
    private lateinit var mAdapter: AdapterGuardAddNewLate
    private lateinit var mCurrentItem: Items

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGuardAddNewLateBinding.inflate(
            layoutInflater, container,
            false
        )
        setHasOptionsMenu(true)
        mToolbar = mBinding.fragmentGuardAddNewLateToolbar
        APP_ACTIVITY.setSupportActionBar(mToolbar)
        mCurrentItem = arguments?.getSerializable("item") as Items
        mToolbar.title = mCurrentItem.objectName
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialization()
        getGuardData()
        Log.d(TAG, "$javaClass + onStart")
    }

    private fun initialization() {
        mAdapter = AdapterGuardAddNewLate()
        mAdapter.senCurrentItem(mCurrentItem)
        mRecyclerView = mBinding.rvFragmentGuardAddNewLate
        mToolbar.setupWithNavController(findNavController())
        mRecyclerView.adapter = mAdapter
    }


    private fun getGuardData() = CoroutineScope(Dispatchers.IO).launch {
        try {
            val list = REPOSITORY_ROOM.getMainGuardList()
            Log.d(TAG, " + getMainGuardList + ${list.size}")
            mList = list.toMutableList()
            withContext(Dispatchers.Main) {
                mAdapter.setList(mList)
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                e.message?.let { showToast(it) }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_guard_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val menuItem = menu.findItem(R.id.fragmentGuard_search)
        mSearchView = menuItem.actionView as SearchView
        searching(mSearchView)
        super.onPrepareOptionsMenu(menu)
    }

    private fun searching(search: SearchView) {
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                mAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                mAdapter.filter.filter(newText)
                return true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mRecyclerView.adapter = null
        Log.d(TAG, "stop: $javaClass")
    }

    companion object {
        fun showDialog(guard: Guards, mItem: Items) {

            val stringTime = SimpleDateFormat("HH:mm, dd MMM.yyyy", Locale.getDefault())
                .format(Date())

            GUARD.guardName = guard.guardName
            GUARD.guardWorkPlace = mItem.objectName
            GUARD.serverTimeStamp = stringTime
            GUARD.state = stateLate
            GUARD.guardKurator = guard.guardKurator

            val builder = AlertDialog.Builder(APP_ACTIVITY)
            builder.setTitle("Вы уверены!")
            builder.setMessage("Что хотите добавить ${guard.guardName} в список Опоздавших?")
            builder.setPositiveButton("Да")
            { _: DialogInterface, _: Int ->

                insertGuardLateRoom(GUARD)
                APP_ACTIVITY.navController
                    .navigate(R.id.action_fragmentGuardAddNewLate_to_viewPagerFragment)
            }
            builder.setNegativeButton("Нет")
            { _: DialogInterface, _: Int -> }
            builder.show()
        }

        private fun insertGuardLateRoom(guard: Guards) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    REPOSITORY_ROOM.insertGuard(guard)
                    withContext(Dispatchers.Main) {
                        showToast("Охранник ${guard.guardName} сохранен как опоздавший")
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        e.message?.let { showToast(it) }
                    }
                }
            }
        }
    }
}