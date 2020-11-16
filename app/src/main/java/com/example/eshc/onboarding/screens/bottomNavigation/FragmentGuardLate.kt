package com.example.eshc.onboarding.screens.bottomNavigation

import android.os.Bundle
import android.util.Log
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
import com.example.eshc.adapters.AdapterGuardLate
import com.example.eshc.databinding.FragmentGuardLateBinding
import com.example.eshc.model.Guards
import com.example.eshc.utilits.TAG


class FragmentGuardLate : Fragment() {

    private var _binding: FragmentGuardLateBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var mAdapter: AdapterGuardLate
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mObserveList: Observer<List<Guards>>
    private lateinit var mViewModel: FragmentGuardLateViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGuardLateBinding.inflate(layoutInflater, container, false)

        mBinding.fragmentGuardLateToolbar.setupWithNavController(findNavController())
        mBinding.fragmentGuardLateToolbar.title =
            resources.getString(R.string.frag_guard_late_toolbar_title)
        // getData()

        Log.d(TAG, "onCreateView: ")
        return mBinding.root
    }
/*
    private fun getData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val itemList = roomRepository.allGuardsLate()
                withContext(Dispatchers.Main){
                    adapterGuardLate = AdapterGuardLate(itemList.asReversed())
                    mBinding.rvFragmentGuardLate.layoutManager = LinearLayoutManager(context)
                    mBinding.rvFragmentGuardLate.adapter = adapterGuardLate
                }
                Log.d(TAG, "CoroutineScope: ")
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showToast(e.message.toString())
                }
            }
        }
    }


 */

    override fun onStart() {
        super.onStart()
        initialization()
    }

    private fun initialization() {
        mAdapter = AdapterGuardLate()
        mRecyclerView = mBinding.rvFragmentGuardLate
        mRecyclerView.adapter = mAdapter
        mObserveList = Observer {
            val list = it.asReversed()
            mAdapter.setList(list)
        }
        mViewModel = ViewModelProvider(this)
            .get(FragmentGuardLateViewModel::class.java)
        mViewModel.allGuardsLate.observe(this, mObserveList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mViewModel.allGuardsLate.removeObserver(mObserveList)
        mRecyclerView.adapter = null
    }
}