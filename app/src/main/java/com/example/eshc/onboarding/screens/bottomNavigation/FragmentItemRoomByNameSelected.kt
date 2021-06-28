package com.example.eshc.onboarding.screens.bottomNavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.eshc.adapters.AdapterItemsRoom
import com.example.eshc.databinding.FragmentItemRoomSingleBinding
import com.example.eshc.model.Items
import com.example.eshc.utilits.REPOSITORY_ROOM
import com.example.eshc.utilits.showToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentItemRoomByNameSelected : Fragment() {
    private var _binding: FragmentItemRoomSingleBinding? = null
    private val mBinding get() = _binding!!
    private var name: String = ""

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mToolbar: Toolbar
    private lateinit var mAdapterItemsRoom: AdapterItemsRoom
    private lateinit var mCurrentItem: Items

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemRoomSingleBinding
            .inflate(layoutInflater, container, false)
        mCurrentItem = arguments?.getSerializable("item") as Items
        name = mCurrentItem.objectName
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialise()
        getData(name)
    }

    /*
    override fun onStart() {
        super.onStart()
        initialise()
        getData(name)
    }

     */

    private fun initialise() {
        mAdapterItemsRoom = AdapterItemsRoom()
        mRecyclerView = mBinding.rvFragmentItemRoomSingle
        mRecyclerView.adapter = mAdapterItemsRoom
        mToolbar = mBinding.fragmentItemRoomSingleToolbar
        mToolbar.title = name
        mToolbar.setupWithNavController(findNavController())
    }

    private fun getData(name: String) = CoroutineScope(Dispatchers.IO).launch {

        try {
            val list = REPOSITORY_ROOM.singleChangedItem(name)
            withContext(Dispatchers.Main) {
                mAdapterItemsRoom.setList(list.asReversed())
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                e.message?.let { showToast(it) }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mRecyclerView.adapter = null
        _binding = null
    }
}