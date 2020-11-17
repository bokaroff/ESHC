package com.example.eshc.onboarding.screens.bottomNavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.eshc.R
import com.example.eshc.adapters.AdapterItems
import com.example.eshc.databinding.FragmentItemRoomBinding
import com.example.eshc.model.Items

class FragmentItemRoom : Fragment() {
    private var _binding: FragmentItemRoomBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var mViewModel: FragmentItemRoomViewModel
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapterItems: AdapterItems
    private lateinit var mObserveList: Observer<List<Items>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentItemRoomBinding.inflate(layoutInflater, container, false)

        mBinding.fragmentItemRoomToolbar.setupWithNavController(findNavController())
        mBinding.fragmentItemRoomToolbar.title = resources.getString(R.string.frag_itemRoom_toolbar_title)

        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialization()
    }

    private fun initialization() {
        mAdapterItems = AdapterItems()
        mRecyclerView = mBinding.rvFragmentItemRoom
        mObserveList  = Observer {
            val list = it.asReversed()
            mAdapterItems.setList(list)
            mRecyclerView.adapter = mAdapterItems
        }
        mViewModel = ViewModelProvider(this)
            .get(FragmentItemRoomViewModel::class.java)
        mViewModel.allItems.observe(this, mObserveList)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mViewModel.allItems.removeObserver(mObserveList)
        mRecyclerView.adapter = null
    }
}