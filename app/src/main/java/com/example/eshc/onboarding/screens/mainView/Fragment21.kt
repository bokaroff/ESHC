package com.example.eshc.onboarding.screens.mainView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.eshc.adapters.AdapterItems
import com.example.eshc.databinding.Fragment21Binding
import com.example.eshc.model.Items
import com.example.eshc.utilits.field_21
import com.example.eshc.utilits.insertItemChangesRoom

class Fragment21 : Fragment() {

    private var _binding: Fragment21Binding? = null
    private val mBinding get() = _binding!!
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapterItems: AdapterItems
    private lateinit var mObserveList: Observer<List<Items>>
    private lateinit var mViewModel: Fragment21ViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = Fragment21Binding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialization()
        getData21()
        insertItemChangesRoom(field_21)
    }

    private fun initialization() {
        mRecyclerView = mBinding.rvFragment21
        mAdapterItems = AdapterItems()
    }

    private fun getData21() {
        mObserveList = Observer {
            mAdapterItems.setList(it)
            mRecyclerView.adapter = mAdapterItems
        }
        mViewModel = ViewModelProvider(this)
            .get(Fragment21ViewModel::class.java)
        mViewModel.mainItemList21.observe(this, mObserveList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mViewModel.mainItemList21.removeObserver(mObserveList)
        mRecyclerView.adapter = null
    }
}