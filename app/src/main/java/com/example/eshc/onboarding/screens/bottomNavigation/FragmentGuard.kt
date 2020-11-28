package com.example.eshc.onboarding.screens.bottomNavigation

import android.os.Bundle
import android.util.Log
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
import com.example.eshc.adapters.FireGuardAdapter
import com.example.eshc.databinding.FragmentGuardBinding
import com.example.eshc.model.Guards
import com.example.eshc.utilits.TAG
import com.example.eshc.utilits.adapterFireGuard
import com.example.eshc.utilits.collectionGUARDS_REF
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObjects


class FragmentGuard : Fragment() {

    private var _binding: FragmentGuardBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var mToolbar: Toolbar
    private lateinit var mEdTxt: EditText
    private lateinit var mRecyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGuardBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getData()
    }

    private fun getData() {
        val queryGuards = collectionGUARDS_REF
            .orderBy("guardName", Query.Direction.ASCENDING)
        val optionsGuards = FirestoreRecyclerOptions.Builder<Guards>()
            .setQuery(queryGuards, Guards::class.java)
            .build()

        adapterFireGuard = FireGuardAdapter(optionsGuards)


    }

    override fun onStart() {
        super.onStart()
        initialization()
        changeQuery()
    }

    private fun changeQuery() {

        mEdTxt.addTextChangedListener {

            val query = collectionGUARDS_REF.whereEqualTo("guardName", it.toString())
            Log.d(TAG, "changeQuery: $it")

            val optionsGuardsChanged = FirestoreRecyclerOptions.Builder<Guards>()
                .setQuery(query, Guards::class.java)
                .build()

                adapterFireGuard.updateOptions(optionsGuardsChanged)

        }




    }

    private fun initialization() {
        mToolbar = mBinding.fragmentGuardToolbar
        mEdTxt = mBinding.edTxtSearch
        mRecyclerView = mBinding.rvFragmentGuard
        mToolbar.setupWithNavController(findNavController())
        mRecyclerView.adapter = adapterFireGuard
        adapterFireGuard.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapterFireGuard.stopListening()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}