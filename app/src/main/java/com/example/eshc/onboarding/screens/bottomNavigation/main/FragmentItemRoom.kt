package com.example.eshc.onboarding.screens.bottomNavigation.main

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.eshc.R
import com.example.eshc.adapters.AdapterItemsRoomComplete
import com.example.eshc.databinding.FragmentItemRoomBinding
import com.example.eshc.model.Items
import com.example.eshc.utilits.APP_ACTIVITY

class FragmentItemRoom : Fragment() {

    private var _binding: FragmentItemRoomBinding? = null
    private val mBinding get() = _binding!!

    private lateinit var mViewModel: FragmentItemRoomViewModel
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mToolbar: Toolbar
    private lateinit var mAdapterItemsRoomComplete: AdapterItemsRoomComplete
    private lateinit var mObserveList: Observer<List<Items>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemRoomBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialise()
        getData()
    }

    /*
    override fun onStart() {
        super.onStart()
        initialise()
        getData()
    }

     */

    private fun initialise() {
        mToolbar = mBinding.fragmentItemRoomToolbar
        mAdapterItemsRoomComplete = AdapterItemsRoomComplete()
        mRecyclerView = mBinding.rvFragmentItemRoom
        APP_ACTIVITY.setSupportActionBar(mToolbar)
    }


    private fun getData() {
        mObserveList = Observer {
            val list = it.asReversed()
            val mutableList = list.toMutableList()
            mAdapterItemsRoomComplete.setList(mutableList)
            mRecyclerView.adapter = mAdapterItemsRoomComplete
        }
        mViewModel = ViewModelProvider(this)
            .get(FragmentItemRoomViewModel::class.java)
        mViewModel.allChangedItems.observe(viewLifecycleOwner, mObserveList)
        mToolbar.setupWithNavController(findNavController())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_item_room_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.fragmentItemRoom_calendar -> {
                APP_ACTIVITY.navController
                    .navigate(R.id.action_fragmentItemRoom_to_fragmentItemRoomBottomSheet)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mViewModel.allChangedItems.removeObserver(mObserveList)
        mRecyclerView.adapter = null
        _binding = null
    }

    companion object {
        fun itemClick(item: Items) {
            val bundle = Bundle()
            bundle.putSerializable("item", item)
            APP_ACTIVITY.navController
                .navigate(R.id.action_fragmentItemRoom_to_fragmentItemRoomSingle, bundle)
        }
    }
}