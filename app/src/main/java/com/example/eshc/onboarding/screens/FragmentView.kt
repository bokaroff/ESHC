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
import com.example.eshc.adapters.FireRecyclerAdapter
import com.example.eshc.databinding.FragmentViewBinding
import com.example.eshc.model.Items
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class FragmentView : Fragment() {
    private var _binding: FragmentViewBinding? = null
    private val mBinding get() = _binding!!

    private lateinit var DB: FirebaseFirestore
    private lateinit var options: FirestoreRecyclerOptions<Items>
    private lateinit var adapter: FireRecyclerAdapter<Items, FireRecyclerAdapter.ItemViewHolder>
    private lateinit var query: Query


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentViewBinding.inflate(layoutInflater, container, false)

        mBinding.fragmentViewToolbar.setupWithNavController(findNavController())
        mBinding.fragmentViewToolbar.title = resources.getString(R.string.frag_view_toolbar_title)
        mBinding.ryFragmentView.layoutManager = LinearLayoutManager(context)
        mBinding.ryFragmentView.adapter = adapter

        return mBinding.root
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB = FirebaseFirestore.getInstance()
        query = DB.collection("Items").orderBy("objectName", Query.Direction.ASCENDING)
        options = FirestoreRecyclerOptions.Builder<Items>()
            .setQuery(query, Items::class.java)
            .build()
        adapter = FireRecyclerAdapter(options)

        DB.collection("Items").addSnapshotListener { value, error ->
        }
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}