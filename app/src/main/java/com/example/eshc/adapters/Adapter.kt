package com.example.eshc.adapters


import android.content.Context
import android.opengl.Visibility
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.eshc.R
import com.example.eshc.model.Items
import com.example.eshc.onboarding.screens.Fragment15
import com.example.eshc.utilits.TAG
import com.example.eshc.utilits.showToast
import kotlinx.android.synthetic.main.recycler_item.view.*

class Adapter(val itemList: MutableList<Items>) :
    RecyclerView.Adapter<Adapter.SimpleViewHolder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_item, parent, false
        )
        context = parent.context
        return SimpleViewHolder(view)
    }

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        holder.recyclerItemContainer.animation =
            AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation)

        holder.objectName.text = itemList[position].objectName
        holder.kurator.text = itemList[position].kurator
        holder.objectPhone.text = itemList[position].objectPhone
        holder.address.text = itemList[position].address
        holder.worker08.text = itemList[position].worker08
        holder.serverTimestamp.text = itemList[position].worker15
        holder.edit_img.visibility = View.GONE
    }

    override fun getItemCount(): Int {
        return itemList.size
    }


    class SimpleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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