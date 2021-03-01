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
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.recycler_guard.view.*

class FireGuardAdapter<T, U>(options: FirestoreRecyclerOptions<Guards>)
    : FirestoreRecyclerAdapter<Guards, FireGuardAdapter.GuardViewHolder>(options) {

    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_guard, parent, false)
        mContext = parent.context
        return GuardViewHolder(view)
    }

    override fun onBindViewHolder(holder: GuardViewHolder, position: Int, model: Guards) {
        holder.guardRvContainer.animation =
            AnimationUtils.loadAnimation(mContext, R.anim.fade_scale_animation)

        holder.guardName.text = model.guardName
        holder.guardPhone.text = model.guardPhone
        holder.guardPhone2.text = model.guardPhone_2
        holder.guardKurator.text = model.guardKurator
        holder.guardWorkPlace.text = model.guardWorkPlace

    }

    class GuardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val guardName: TextView = itemView.guardName_txt
        val guardPhone: TextView = itemView.guardPhone_txt
        val guardPhone2: TextView = itemView.guardPhone2_txt
        val guardKurator: TextView = itemView.guard_kurator_txt
        val guardWorkPlace: TextView = itemView.guard_work_txt
        val guardRvContainer: ConstraintLayout = itemView.recycler_item_guard_container
    }

}