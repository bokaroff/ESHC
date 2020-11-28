package com.example.eshc.onboarding.screens.bottomNavigation

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.eshc.R
import com.example.eshc.adapters.AdapterGuardLate
import com.example.eshc.databinding.FragmentGuardLateBinding
import com.example.eshc.model.Guards
import com.example.eshc.utilits.TAG
import com.google.android.material.snackbar.Snackbar


class FragmentGuardLate : Fragment() {

    private var _binding: FragmentGuardLateBinding? = null
    private val mBinding get() = _binding!!
    private var mList = mutableListOf<Guards>()
    private var swipeBackground = ColorDrawable(Color.RED)

    private lateinit var mAdapter: AdapterGuardLate
    private lateinit var mToolbar: Toolbar
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mObserveList: Observer<List<Guards>>
    private lateinit var mViewModel: FragmentGuardLateViewModel
    private lateinit var deleteIcon: Drawable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGuardLateBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialization()
        swipeToDelete()
    }

    private fun initialization() {
        mAdapter = AdapterGuardLate()
        deleteIcon = ResourcesCompat.getDrawable(resources,
            R.drawable.ic_delete_white, null)!!

        mToolbar = mBinding.fragmentGuardLateToolbar
        mRecyclerView = mBinding.rvFragmentGuardLate
        mRecyclerView.adapter = mAdapter

        mObserveList = Observer {
            val list = it.asReversed()
            mList = list.toMutableList()
            Log.d(TAG, "initialization: ${mList.size}")
            mAdapter.setList(mList)
        }
        mViewModel = ViewModelProvider(this)
            .get(FragmentGuardLateViewModel::class.java)
        mViewModel.allGuardsLate.observe(this, mObserveList)

        mToolbar.setupWithNavController(findNavController())
    }

    private fun swipeToDelete() {
        val itemTouchHelperCallBack = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, position: Int) {
                val guard = mList[viewHolder.adapterPosition]
                val removedPosition = viewHolder.adapterPosition

                mViewModel.deleteGuardLate(guard)
                mViewModel.allGuardsLate.removeObserver(mObserveList)
                mAdapter.removeItem(viewHolder)

                Snackbar.make(
                    viewHolder.itemView, "${guard.guardName} удален",
                    Snackbar.LENGTH_LONG
                ).setActionTextColor(Color.RED)
                    .setAction("Отмена") {
                        mViewModel.insertGuardLate(guard)
                        mAdapter.insertItem(removedPosition, guard)
                        mRecyclerView.smoothScrollToPosition(removedPosition)
                    }.show()
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val itemView = viewHolder.itemView
                val iconMargin = (itemView.height - deleteIcon.intrinsicHeight) / 2


                if (dX > 0) {
                    swipeBackground.setBounds(
                        itemView.left, itemView.top,
                        dX.toInt(), itemView.bottom
                    )
                    deleteIcon.setBounds(itemView.left + iconMargin, itemView.top + iconMargin,
                    itemView.left + iconMargin + deleteIcon.intrinsicWidth,
                    itemView.bottom - iconMargin)

                } else {
                    swipeBackground.setBounds(
                        itemView.right + dX.toInt(),
                        itemView.top, itemView.right, itemView.bottom
                    )
                    deleteIcon.setBounds(itemView.right - iconMargin - deleteIcon.intrinsicWidth,
                        itemView.top + iconMargin,
                        itemView.right - iconMargin,
                        itemView.bottom - iconMargin)
                }
                swipeBackground.draw(c)
                deleteIcon.draw(c)

                super.onChildDraw(
                    c, recyclerView, viewHolder, dX, dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallBack)
        itemTouchHelper.attachToRecyclerView(mRecyclerView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mViewModel.allGuardsLate.removeObserver(mObserveList)
        mRecyclerView.adapter = null
    }


}

