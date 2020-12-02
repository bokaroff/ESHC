package com.example.eshc.onboarding.screens.bottomNavigation

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.eshc.R
import com.example.eshc.adapters.AdapterGuard
import com.example.eshc.databinding.FragmentGuardBinding
import com.example.eshc.model.Guards
import com.example.eshc.utilits.APP_ACTIVITY
import com.example.eshc.utilits.TAG
import com.example.eshc.utilits.collectionGUARDS_REF
import com.example.eshc.utilits.showToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class FragmentGuard : Fragment() {

    private var _binding: FragmentGuardBinding? = null
    private val mBinding get() = _binding!!
    private var mList = mutableListOf<Guards>()
    private lateinit var mToolbar: Toolbar
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mSearchView: SearchView
    private lateinit var mAdapter: AdapterGuard

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGuardBinding.inflate(
            layoutInflater, container,
            false
        )
        setHasOptionsMenu(true)
        mToolbar = mBinding.fragmentGuardToolbar
        APP_ACTIVITY.setSupportActionBar(mToolbar)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialization()
        getData()
    }

    private fun initialization() {
        mAdapter = AdapterGuard()
        mRecyclerView = mBinding.rvFragmentGuard
        mToolbar.setupWithNavController(findNavController())
        mRecyclerView.adapter = mAdapter
    }


    private fun getData() = CoroutineScope(Dispatchers.IO).launch {
        val list = mutableListOf<Guards>()
        try {
            val query = collectionGUARDS_REF.get().await()
            for (dc in query) {
                val guard = dc.toObject(Guards::class.java)
                list.add(guard)
            }
            withContext(Dispatchers.Main) {
                mAdapter.setList(list)
            }

        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                showToast(e.message.toString())
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
    }
}