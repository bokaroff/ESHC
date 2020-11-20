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
import com.example.eshc.adapters.FireItemAdapter
import com.example.eshc.databinding.FragmentViewBinding
import com.example.eshc.model.Items
import com.example.eshc.utilits.*
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class FragmentView : Fragment() {
    private var _binding: FragmentViewBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mToolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentViewBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getData()
    }

    private fun getData() {
        val query = collectionITEMS_REF
        val options = FirestoreRecyclerOptions.Builder<Items>()
            .setQuery(query, Items::class.java)
            .build()
        adapterFireItem = FireItemAdapter(options)
    }

    override fun onStart() {
        super.onStart()
        initialization()

    }

    private fun initialization() {
        mRecyclerView = mBinding.rvFragmentView
        mToolbar = mBinding.fragmentViewToolbar
        mToolbar.setupWithNavController(findNavController())
        mRecyclerView.adapter = adapterFireItem
        adapterFireItem.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapterFireItem.stopListening()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        fun popupUpdate(item: Items){
                showToast("popupUpdate")
        }
        fun popupAddNewItem (item: Items){
            showToast("popupAddNew")
        }
        fun popupAddLateList(item: Items) {
            GUARD.workPlace= item.objectName
            GUARD.guardName = item.worker08
            GUARD.guardKurator = item.kurator

            insertGuardLateRoom(GUARD)
        }

        fun popupDelete(item: Items){
            showToast("popupDelete")
        }
    }


}