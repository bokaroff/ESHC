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
import com.example.eshc.onboarding.screens.bottomNavigation.main.FragmentItemRoom
import kotlinx.android.synthetic.main.recycler_item_mini.view.*

class AdapterItemsRoomComplete() : RecyclerView.Adapter<AdapterItemsRoomComplete.SimpleViewHolder>() {
    private lateinit var context: Context
    private var mList = mutableListOf<Items>()

    override fun onViewAttachedToWindow(holder: SimpleViewHolder) {
        holder.recyclerItemContainer.setOnClickListener {
            val item = mList[holder.adapterPosition]

            FragmentItemRoom.itemClick(item)
        }
    }

    override fun onViewDetachedFromWindow(holder: SimpleViewHolder) {
        holder.recyclerItemContainer.setOnClickListener(null)
        super.onViewDetachedFromWindow(holder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_item_mini, parent, false
        )
        context = parent.context
        return SimpleViewHolder(view)
    }

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        holder.recyclerItemContainer.animation =
            AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation)

        holder.objectName.text = mList[position].objectName
        holder.worker.text = mList[position].worker08
        holder.serverTime.text = mList[position].serverTimeStamp
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class SimpleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val objectName: TextView = itemView.objectName_mini
        val worker: TextView = itemView.phone_mini
        val serverTime: TextView = itemView.mobile_mini
        val recyclerItemContainer: ConstraintLayout = itemView.rv_item_container
    }

    fun setList(list: List<Items>) {
        mList = list.toMutableList()
        notifyDataSetChanged()
    }
/*
    fun removeItem(position: Int, item: Items, mutableList: MutableList<Items>) {
        mList.remove(item)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, mutableList.size)
    }

 */
}