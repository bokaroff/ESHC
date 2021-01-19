package com.example.eshc.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.eshc.R
import com.example.eshc.model.Items
import kotlinx.android.synthetic.main.recycler_item.view.*

class AdapterItems() : RecyclerView.Adapter<AdapterItems.SimpleViewHolder>() {
    private lateinit var context: Context
    private var mList = mutableListOf<Items>()

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

        holder.objectName.text = mList[position].objectName
        holder.kurator.text = mList[position].kurator
        holder.objectPhone.text = mList[position].objectPhone
        holder.mobilePhone.text = mList[position].mobilePhone
        holder.address.text = mList[position].address
        holder.worker08.text = mList[position].worker08
        holder.serverTimestamp.text = mList[position].worker15
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class SimpleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val objectName: TextView = itemView.objectName_txt
        val kurator: TextView = itemView.kurator_txt
        val objectPhone: TextView = itemView.objectPhone_txt
        val mobilePhone: TextView = itemView.mobilePhone_txt
        val address: TextView = itemView.address_txt
        val worker08: TextView = itemView.worker08_txt
        val serverTimestamp: TextView = itemView.serverTimestamp_txt
        val recyclerItemContainer: ConstraintLayout = itemView.recycler_item_container
    }

    fun setList(list: MutableList<Items>) {
        mList = list
        notifyDataSetChanged()
    }

    fun removeItem(item: Items) {
        mList.remove(item)
        notifyDataSetChanged()


    }
}