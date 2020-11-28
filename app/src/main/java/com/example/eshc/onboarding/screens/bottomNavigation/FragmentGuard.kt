package com.example.eshc.onboarding.screens.bottomNavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.eshc.adapters.AdapterGuard
import com.example.eshc.databinding.FragmentGuardBinding
import com.example.eshc.model.Guards
import com.example.eshc.utilits.collectionGUARDS_REF
import com.example.eshc.utilits.showToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class FragmentGuard : Fragment() {

    private var _binding: FragmentGuardBinding? = null
    private val mBinding get() = _binding!!
    private var mList = mutableListOf<Guards>()
    private lateinit var mToolbar: Toolbar
    private lateinit var mEdTxt: EditText
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: AdapterGuard


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGuardBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }



    override fun onStart() {
        super.onStart()
        initialization()
        getData()
        changeQuery()
    }

    private fun initialization() {
        mToolbar = mBinding.fragmentGuardToolbar
        mEdTxt = mBinding.edTxtSearch
        mAdapter = AdapterGuard()
        mRecyclerView = mBinding.rvFragmentGuard
        mToolbar.setupWithNavController(findNavController())
        mRecyclerView.adapter = mAdapter
    }

    private fun getData() = CoroutineScope(Dispatchers.IO).launch {
        val list = mutableListOf<Guards>()
        try {
            val query = collectionGUARDS_REF.get().await()
            for (dc in query) {
                val guard = dc.toObject(Guards::class.java)
                list.add(guard)
            }
            withContext(Dispatchers.Main) {
                mAdapter.setList(list)
            }

        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                showToast(e.message.toString())
            }
        }
    }

    private fun changeQuery() {
        mEdTxt.addTextChangedListener {

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mRecyclerView.adapter = null
    }
}