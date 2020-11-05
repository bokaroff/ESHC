package com.example.eshc.onboarding.screens.bottomNavigation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eshc.R
import com.example.eshc.adapters.AdapterGuardLate
import com.example.eshc.databinding.FragmentGuardLateBinding
import com.example.eshc.utilits.ITEM_ROOM_REPOSITORY
import com.example.eshc.utilits.TAG
import com.example.eshc.utilits.showToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FragmentGuardLate : Fragment() {

    private lateinit var adapterGuardLate: AdapterGuardLate
    private var _binding: FragmentGuardLateBinding? = null
    private val mBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGuardLateBinding.inflate(layoutInflater, container, false)

        mBinding.fragmentGuardLateToolbar.setupWithNavController(findNavController())
        mBinding.fragmentGuardLateToolbar.title =
            resources.getString(R.string.frag_guard_late_toolbar_title)
        getData()

        Log.d(TAG, "onCreateView: ")
        return mBinding.root
    }

    private fun getData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val itemList = ITEM_ROOM_REPOSITORY.getAllGuardsLate()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d(TAG, "onDestroyView: ")
    }
}