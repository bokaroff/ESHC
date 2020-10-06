package com.example.eshc.adapters

import android.content.ClipData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.eshc.R
import com.example.eshc.model.Items
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class FireRecyclerAdapter<T, U>(options: FirestoreRecyclerOptions<Items>)
    : FirestoreRecyclerAdapter<Items, FireRecyclerAdapter.ItemViewHolder>(options) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent,
          false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int, model: Items) {

    }


    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    }
}