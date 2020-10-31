package com.example.eshc.onboarding.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eshc.R
import com.example.eshc.adapters.FireItemAdapter
import com.example.eshc.databinding.FragmentViewBinding
import com.example.eshc.model.Items
import com.example.eshc.utilits.APP_ACTIVITY
import com.example.eshc.utilits.adapterFireItem
import com.example.eshc.utilits.collectionITEMS_REF
import com.example.eshc.utilits.showToast
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class FragmentView : Fragment() {
    private var _binding: FragmentViewBinding? = null
    private val mBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentViewBinding.inflate(layoutInflater, container, false)

        mBinding.fragmentViewToolbar.setupWithNavController(findNavController())
        mBinding.fragmentViewToolbar.title = resources.getString(R.string.frag_view_toolbar_title)
        mBinding.ryFragmentView.layoutManager = LinearLayoutManager(context)
        mBinding.ryFragmentView.adapter = adapterFireItem

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
        fun popupAddNew(item: Items){
            showToast("popupAddNew")
        }
        fun popupAddLateList(item: Items){
           // showToast("popupAddLateList")
            val bundle = Bundle()
            bundle.putSerializable("item", item)
            APP_ACTIVITY.navController.navigate(R.id.action_fragmentView_to_fragmentStaffLate, bundle)
        }
        fun popupDelete(item: Items){
            showToast("popupDelete")
        }
    }
}