package com.example.eshc.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.eshc.R
import com.example.eshc.databinding.RecyclerItemBinding
import com.example.eshc.model.Items
import com.example.eshc.onboarding.screens.bottomNavigation.main.FragmentView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.imageview.ShapeableImageView


class FireItemAdapter<T, U>(options: FirestoreRecyclerOptions<Items>) :
    FirestoreRecyclerAdapter<Items, FireItemAdapter.ItemViewHolder>(options) {

    private lateinit var mContext: Context

    override fun onViewAttachedToWindow(holder: ItemViewHolder) {
        holder.img.setOnClickListener {
            val item = getItem(holder.adapterPosition)
            FragmentView.popUpFragmentClick(item)
        }

        holder.objectPhone.setOnClickListener {
            val objectPhoneNumber = getItem(holder.adapterPosition).objectPhone
            FragmentView.startPhoneDial(objectPhoneNumber)
        }

        holder.mobilePhone.setOnClickListener {
            val mobilePhoneNumber = getItem(holder.adapterPosition).mobilePhone
            FragmentView.startPhoneDial(mobilePhoneNumber)
        }
    }

    override fun onViewDetachedFromWindow(holder: ItemViewHolder) {
        holder.img.setOnClickListener(null)
        holder.objectPhone.setOnClickListener(null)
        holder.mobilePhone.setOnClickListener(null)
        super.onViewDetachedFromWindow(holder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = ItemViewHolder(
            RecyclerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        mContext = parent.context
        return view
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int, model: Items) {

        holder.img.animation =
            AnimationUtils.loadAnimation(mContext, R.anim.fade_transition_animation)

        holder.recyclerItemContainer.animation =
            AnimationUtils.loadAnimation(mContext, R.anim.fade_scale_animation)

        holder.objectName.text = model.objectName
        holder.kurator.text = model.kurator
        holder.objectPhone.text = model.objectPhone
        holder.mobilePhone.text = model.mobilePhone
        holder.address.text = model.address
        holder.worker08.text = model.worker08
        holder.serverTimestamp.text = model.worker15
    }

    class ItemViewHolder(binding: RecyclerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val objectName: TextView = binding.nameTxt
        val kurator: TextView = binding.kuratorTxt
        val objectPhone: TextView = binding.phone
        val mobilePhone: TextView = binding.mobile
        val address: TextView = binding.addressTxt
        val worker08: TextView = binding.worker08Txt
        val serverTimestamp: TextView = binding.serverTimestampTxt
        val recyclerItemContainer: ConstraintLayout = binding.rvItemContainer
        val img: ShapeableImageView = binding.ivItemLogo
    }
}