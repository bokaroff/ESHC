package com.example.eshc.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.eshc.R
import com.example.eshc.databinding.RvItemRoomMiniBinding
import com.example.eshc.model.Items

class AdapterItemsRoom() : RecyclerView.Adapter<AdapterItemsRoom.SimpleViewHolder>() {
    private lateinit var mContext: Context
    private var mList = mutableListOf<Items>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        val view = SimpleViewHolder(
            RvItemRoomMiniBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        mContext = parent.context
        return view
    }

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        holder.recyclerItemContainer.animation =
            AnimationUtils.loadAnimation(mContext, R.anim.fade_scale_animation)

        holder.objectName.text = mList[position].objectName
        holder.kurator.text = mList[position].kurator
        holder.worker.text = mList[position].worker08
        holder.serverTime.text = mList[position].serverTimeStamp
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class SimpleViewHolder(binding: RvItemRoomMiniBinding) : RecyclerView.ViewHolder(binding.root) {
        val objectName: TextView = binding.objectNameRoomMini
        val kurator: TextView = binding.kuratorRoomMini
        val worker: TextView = binding.workerRoomMini
        val serverTime: TextView = binding.timeRoomMini
        val recyclerItemContainer: ConstraintLayout = binding.rvItemRoomContainerMini
    }

    fun setList(list: List<Items>) {
        mList = list.toMutableList()
        notifyDataSetChanged()
    }
}