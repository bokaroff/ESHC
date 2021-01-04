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
import com.example.eshc.databinding.Fragment00Binding
import com.example.eshc.model.Items
import com.example.eshc.utilits.TAG
import com.example.eshc.utilits.field_00
import com.example.eshc.utilits.insertItemChangesRoom


class Fragment00 : Fragment() {

    private var _binding: Fragment00Binding? = null
    private val mBinding get() = _binding!!
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapterItems: AdapterItems
    private lateinit var mObserveList: Observer<List<Items>>
    private lateinit var mViewModel: Fragment00ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = Fragment00Binding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialization()
        getData00()
        insertItemChangesRoom(field_00)
    }

    private fun initialization() {
        mRecyclerView = mBinding.rvFragment00
        mAdapterItems = AdapterItems()
    }

    private fun getData00() {
        mObserveList = Observer {
            mAdapterItems.setList(it)
            mRecyclerView.adapter = mAdapterItems
        }
        mViewModel = ViewModelProvider(this)
            .get(Fragment00ViewModel::class.java)
        mViewModel.mainItemList00.observe(this, mObserveList)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mViewModel.mainItemList00.removeObserver(mObserveList)
        mRecyclerView.adapter = null
        Log.d(TAG, "stop: $javaClass")
    }
}