package com.example.eshc.onboarding.screens.bottomNavigation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.eshc.R
import com.example.eshc.adapters.AdapterGuardLate
import com.example.eshc.adapters.AdapterGuardLateComplete
import com.example.eshc.databinding.ActivityMainBinding.inflate
import com.example.eshc.databinding.FragmentGuardLateBinding
import com.example.eshc.databinding.FragmentGuardLateByNameBinding
import com.example.eshc.model.Guards
import com.example.eshc.model.Items
import com.example.eshc.utilits.REPOSITORY_ROOM
import com.example.eshc.utilits.TAG
import com.example.eshc.utilits.showToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentGuardLateByName : Fragment() {

    private var _binding: FragmentGuardLateByNameBinding? = null
    private val mBinding get() = _binding!!
    private var name: String = ""

    private lateinit var mAdapter: AdapterGuardLate
    private lateinit var mToolbar: Toolbar
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mCurrentGuardLate: Guards

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGuardLateByNameBinding.inflate(layoutInflater, container, false)
        mCurrentGuardLate = arguments?.getSerializable("guard") as Guards
        name = mCurrentGuardLate.guardName
        return mBinding.root
    }





    override fun onStart() {
        super.onStart()
        initialise()
        getData(name)
    }

    private  fun initialise(){
        mAdapter = AdapterGuardLate()
        mToolbar = mBinding.fragmentGuardLateByNameToolbar
        mRecyclerView = mBinding.rvFragmentGuardLateByName
        mRecyclerView.adapter = mAdapter
        mToolbar.title = name
        mToolbar.setupWithNavController(findNavController())
    }

    private fun getData(name: String) = CoroutineScope(Dispatchers.IO).launch {

        try {
            val list = REPOSITORY_ROOM.singleGuardLateByName(name)
            val mutableList = list.toMutableList()
            Log.d(TAG, " + singleGuardLateByName + ${list.size}")
            withContext(Dispatchers.Main) {
                mAdapter.setList(mutableList.asReversed())
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                e.message?.let { showToast(it) }
            }
        }
    }









    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mRecyclerView.adapter = null
    }





}