package com.example.eshc.onboarding.screens.bottomNavigation

import android.content.Context
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
import com.example.eshc.adapters.AdapterItemsRoom
import com.example.eshc.databinding.FragmentItemRoomBinding
import com.example.eshc.model.Items
import com.example.eshc.utilits.APP_ACTIVITY

class FragmentItemRoom : Fragment() {

    private var day = 0
    private var month = 0
    private var year = 0

    private var savedDay = 0
    private var savedMonth = 0
    private var savedYear = 0

    private var _binding: FragmentItemRoomBinding? = null
    private val mBinding get() = _binding!!

    private var _context: Context? = null
    private val mContext get() = _context!!

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

        if (container != null) {
            _context = container.context
        }

        setHasOptionsMenu(true)
        mToolbar = mBinding.fragmentItemRoomToolbar
        APP_ACTIVITY.setSupportActionBar(mToolbar)
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
        _binding = null
        mViewModel.allChangedItems.removeObserver(mObserveList)
        mRecyclerView.adapter = null
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