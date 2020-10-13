package com.example.eshc.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.eshc.R
import com.example.eshc.model.Items
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.recycler_item.view.*

class FireItemAdapter<T, U>(options: FirestoreRecyclerOptions<Items>)
    : FirestoreRecyclerAdapter<Items, FireItemAdapter.ItemViewHolder>(options) {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
      val view = LayoutInflater.from(parent.context).inflate(
          R.layout.recycler_item, parent, false)
        context = parent.context
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int, model: Items) {
        holder.recyclerItemContainer.animation = AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation)

        holder.objectName.text = model.objectName
        holder.kurator.text = model.kurator
        holder.objectPhone.text = model.objectPhone
        holder.address.text = model.address
        holder.worker08.text = model.worker08
        holder.serverTimestamp.text = model.worker15
    }



    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       val objectName = itemView.objectName_txt
        val kurator = itemView.kurator_txt
        val objectPhone = itemView.objectPhone_txt
        val address = itemView.address_txt
        val worker08 = itemView.worker08_txt
        val serverTimestamp = itemView.serverTimestamp_txt
        val edit_img = itemView.recycler_edit_img
        val recyclerItemContainer = itemView.recycler_item_container
    }
}