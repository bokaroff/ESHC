package com.example.eshc.onboarding.screens.bottomNavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.eshc.adapters.AdapterItems
import com.example.eshc.adapters.AdapterItemsRoom
import com.example.eshc.databinding.FragmentItemRoomBinding
import com.example.eshc.model.Items
import com.example.eshc.utilits.APP_ACTIVITY

class FragmentItemRoom : Fragment() {
    private var _binding: FragmentItemRoomBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var mViewModel: FragmentItemRoomViewModel
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mToolbar: Toolbar
    private lateinit var mAdapterItemsRoom: AdapterItemsRoom
    private lateinit var mObserveList: Observer<List<Items>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentItemRoomBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialization()
        getData()
    }

    private fun initialization() {
        mAdapterItemsRoom = AdapterItemsRoom()
        mRecyclerView = mBinding.rvFragmentItemRoom
        mToolbar = mBinding.fragmentItemRoomToolbar
    }

    private fun getData() {
        mObserveList = Observer {
            val list = it.asReversed()
            val mutableList = list.toMutableList()
            mAdapterItemsRoom.setList(mutableList)
            mRecyclerView.adapter = mAdapterItemsRoom
        }
        mViewModel = ViewModelProvider(this)
            .get(FragmentItemRoomViewModel::class.java)
        mViewModel.allChangedItems.observe(this, mObserveList)
        mToolbar.setupWithNavController(findNavController())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mViewModel.allChangedItems.removeObserver(mObserveList)
        mRecyclerView.adapter = null
    }
}