package com.example.eshc.onboarding.screens.mainView

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.eshc.adapters.AdapterItems
import com.example.eshc.databinding.Fragment02Binding
import com.example.eshc.model.Items
import com.example.eshc.utilits.TAG


class Fragment02 : Fragment() {

    private var _binding: Fragment02Binding? = null
    private val mBinding get() = _binding!!
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapterItems: AdapterItems
    private lateinit var mObserveList: Observer<List<Items>>
    private lateinit var mViewModel: Fragment02ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = Fragment02Binding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialization()
        getData02()
    }

    private fun initialization() {
        mRecyclerView = mBinding.rvFragment02
        mAdapterItems = AdapterItems()
    }

    private fun getData02() {

        mObserveList = Observer {
            mAdapterItems.setList(it)
            mRecyclerView.adapter = mAdapterItems
        }
        mViewModel = ViewModelProvider(this)
            .get(Fragment02ViewModel::class.java)
        mViewModel.mainItemList02.observe(this, mObserveList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mViewModel.mainItemList02.removeObserver(mObserveList)
        mRecyclerView.adapter = null
        Log.d(TAG, "stop: $javaClass")
    }
}