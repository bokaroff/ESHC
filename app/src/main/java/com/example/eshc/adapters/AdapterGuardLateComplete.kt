package com.example.eshc.adapters


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.eshc.R
import com.example.eshc.databinding.RecyclerGuardLateBinding
import com.example.eshc.model.Guards
import com.example.eshc.onboarding.screens.bottomNavigation.main.FragmentGuardLate
import com.example.eshc.utilits.TAG
import java.util.*

class AdapterGuardLateComplete : RecyclerView.Adapter<AdapterGuardLateComplete.SimpleViewHolder>(),
    Filterable {
    private lateinit var mContext: Context
    private var mList = mutableListOf<Guards>()
    private var mListFiltered = mutableListOf<Guards>()

    override fun onViewAttachedToWindow(holder: SimpleViewHolder) {
        holder.rvGuardLateContainer.setOnClickListener {
            val guard = mListFiltered[holder.adapterPosition]

            FragmentGuardLate.itemClick(guard)
        }
    }

    override fun onViewDetachedFromWindow(holder: SimpleViewHolder) {
        holder.rvGuardLateContainer.setOnClickListener(null)
        super.onViewDetachedFromWindow(holder)
    }

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

        holder.guardLateName.text = mListFiltered[position].guardName
        holder.guardLateKurator.text = mListFiltered[position].guardKurator
        holder.guardLateWork.text = mListFiltered[position].guardWorkPlace
        holder.guardLateTime.text = mListFiltered[position].serverTimeStamp
    }

    override fun getItemCount(): Int {
        return mListFiltered.size
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
        mListFiltered = list
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {

                val key = charSequence.toString().toLowerCase(Locale.ROOT).trim()

                mListFiltered = if (key.isEmpty()) {
                    mList
                } else {
                    val newList = mutableListOf<Guards>()
                    for (guard in mList) {
                        val name = guard.guardName.toLowerCase(Locale.ROOT).trim()
                        if (name.contains(key)) {
                            Log.d(TAG, "contains: + $key")
                            newList.add(guard)
                        }
                    }
                    newList
                }
                val filterResults = FilterResults()
                filterResults.values = mListFiltered
                filterResults.count = mListFiltered.size
                return filterResults
            }

            override fun publishResults(
                charSequence: CharSequence?,
                filterResults: FilterResults?
            ) {
                mListFiltered = filterResults?.values as MutableList<Guards>
                notifyDataSetChanged()
            }
        }
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