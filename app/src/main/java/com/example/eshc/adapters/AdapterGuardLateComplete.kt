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
import com.example.eshc.model.Guards
import com.example.eshc.onboarding.screens.bottomNavigation.main.FragmentGuardLate
import kotlinx.android.synthetic.main.recycler_guard_late.view.*

class AdapterGuardLateComplete : RecyclerView.Adapter<AdapterGuardLateComplete.SimpleViewHolder>() {
    private lateinit var context: Context
    private var mList = mutableListOf<Guards>()

    override fun onViewAttachedToWindow(holder: AdapterGuardLateComplete.SimpleViewHolder) {
        holder.rvGuardLateContainer.setOnClickListener {
            val guard = mList[holder.adapterPosition]

            FragmentGuardLate.itemClick(guard)
        }
    }

    override fun onViewDetachedFromWindow(holder: AdapterGuardLateComplete.SimpleViewHolder) {
        holder.rvGuardLateContainer.setOnClickListener(null)
        super.onViewDetachedFromWindow(holder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_guard_late, parent, false
        )
        context = parent.context
        return SimpleViewHolder(view)
    }

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        holder.rvGuardLateContainer.animation =
            AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation)

        holder.guardLateName.text = mList[position].guardName
        holder.guardLateKurator.text = mList[position].guardKurator
        holder.guardLateWork.text = mList[position].guardWorkPlace
        holder.guardLateTime.text = mList[position].serverTimeStamp
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class SimpleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rvGuardLateContainer: ConstraintLayout = itemView.rvGuardLateContainer
        val guardLateName: TextView = itemView.guardLateName
        val guardLateKurator: TextView = itemView.guardLateKurator
        val guardLateWork: TextView = itemView.guardLateWork
        val guardLateTime: TextView = itemView.guardLateTime
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