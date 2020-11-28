package com.example.eshc.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.eshc.R
import com.example.eshc.model.Guards
import kotlinx.android.synthetic.main.recycler_guard.view.*
import kotlinx.android.synthetic.main.recycler_guard_late.view.*

class AdapterGuard : RecyclerView.Adapter<AdapterGuard.SimpleViewHolder>() {
    private lateinit var context: Context
    private var mList = mutableListOf<Guards>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_guard, parent, false
        )
        context = parent.context
        return SimpleViewHolder(view)
    }

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        holder.guardRecyclContainer.animation = AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation)

        holder.guardName.text = mList[position].guardName
        holder.guardPhone.text = mList[position].guardPhone
        holder.guardPhone_2.text = mList[position].guardPhone_2
        holder.guardKurator.text = mList[position].guardKurator
        holder.guardWorkPlace.text = mList[position].workPlace
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class SimpleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val guardName = itemView.guardName_txt
        val guardPhone = itemView.guardPhone_txt
        val guardPhone_2 = itemView.guardPhone2_txt
        val guardKurator = itemView.guard_kurator_txt
        val guardWorkPlace = itemView.guard_work_txt
        val guardRecyclContainer = itemView.recycler_item_guard_container
    }

    fun setList(list: MutableList<Guards>) {
        mList = list
        notifyDataSetChanged()
    }

}