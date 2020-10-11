package com.example.eshc.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.eshc.R
import com.example.eshc.model.Guards
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.recycler_item_guard.view.*

class FireGuardAdapter<T, U>(options: FirestoreRecyclerOptions<Guards>)
    : FirestoreRecyclerAdapter<Guards, FireGuardAdapter.GuardViewHolder>(options) {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item_guard, parent, false)
        context = parent.context
        return GuardViewHolder(view)
    }

    override fun onBindViewHolder(holder: GuardViewHolder, position: Int, model: Guards) {
        holder.guardRecyclContainer.animation = AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation)

        holder.guardName.text = model.guardName
        holder.guardPhone.text = model.guardPhone
        holder.guardPhone_2.text = model.guardPhone_2
        holder.guardKurator.text = model.guardKurator
        holder.guardWorkPlace.text = model.workPlace

    }

    class GuardViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val guardName = itemView.guardName_txt
        val guardPhone = itemView.guardPhone_txt
        val guardPhone_2 = itemView.guardPhone2_txt
        val guardKurator = itemView.guard_kurator_txt
        val guardWorkPlace = itemView.guard_work_txt
        val guardEditImg = itemView.recycler_guard_edit_img
        val guardRecyclContainer = itemView.recycler_item_guard_container
    }

}