package com.example.eshc.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.eshc.R
import com.example.eshc.databinding.RecyclerGuardLateBinding
import com.example.eshc.model.Guards

class AdapterGuardLate : RecyclerView.Adapter<AdapterGuardLate.SimpleViewHolder>() {
    private lateinit var mContext: Context
    private var mList = mutableListOf<Guards>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        val view = SimpleViewHolder(
            RecyclerGuardLateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        mContext = parent.context
        return view
    }

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        holder.rvGuardLateContainer.animation =
            AnimationUtils.loadAnimation(mContext, R.anim.fade_scale_animation)

        holder.guardLateName.text = mList[position].guardName
        holder.guardLateKurator.text = mList[position].guardKurator
        holder.guardLateWork.text = mList[position].guardWorkPlace
        holder.guardLateTime.text = mList[position].serverTimeStamp
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class SimpleViewHolder(binding: RecyclerGuardLateBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val rvGuardLateContainer: ConstraintLayout = binding.rvGuardLateContainer
        val guardLateName: TextView = binding.guardLateName
        val guardLateKurator: TextView = binding.guardLateKurator
        val guardLateWork: TextView = binding.guardLateWork
        val guardLateTime: TextView = binding.guardLateTime
    }

    fun setList(list: MutableList<Guards>) {
        mList = list
        notifyDataSetChanged()
    }

    fun removeItem(viewHolder: RecyclerView.ViewHolder) {
        mList.removeAt(viewHolder.adapterPosition)
        notifyItemRemoved(viewHolder.adapterPosition)
    }

    fun insertItem(position: Int, guard: Guards) {
        mList.add(position, guard)
        notifyItemInserted(position)
    }
}