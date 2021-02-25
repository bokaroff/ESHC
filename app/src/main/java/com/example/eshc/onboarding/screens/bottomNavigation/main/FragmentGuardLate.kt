package com.example.eshc.onboarding.screens.bottomNavigation.main

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.eshc.R
import com.example.eshc.adapters.AdapterGuardLateComplete
import com.example.eshc.databinding.FragmentGuardLateBinding
import com.example.eshc.model.Guards
import com.example.eshc.utilits.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class FragmentGuardLate : Fragment() {

    private var _binding: FragmentGuardLateBinding? = null
    private val mBinding get() = _binding!!
    private var mList = mutableListOf<Guards>()
    private var swipeBackground = ColorDrawable(Color.RED)

    private lateinit var mAdapterComplete: AdapterGuardLateComplete
    private lateinit var mToolbar: Toolbar
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mObserveList: Observer<List<Guards>>
    private lateinit var deleteIcon: Drawable
    private lateinit var mSearchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGuardLateBinding.inflate(layoutInflater, container, false)

        setHasOptionsMenu(true)
        mToolbar = mBinding.fragmentGuardLateToolbar
        APP_ACTIVITY.setSupportActionBar(mToolbar)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialise()
        getGuardData()
        swipeToDelete()
    }

    private fun initialise() {
        mAdapterComplete = AdapterGuardLateComplete()
        deleteIcon = ResourcesCompat.getDrawable(
            resources,
            R.drawable.ic_delete_white, null
        )!!

        mRecyclerView = mBinding.rvFragmentGuardLate
        mRecyclerView.adapter = mAdapterComplete
        mToolbar.setupWithNavController(findNavController())
    }

    private fun getGuardData() = CoroutineScope(Dispatchers.IO).launch {
        try {
            val list = REPOSITORY_ROOM.getAllGuardsLate()
            Log.d(TAG, " + getGuardLateList + ${list.size}")
            mList = list.toMutableList().asReversed()
            withContext(Dispatchers.Main) {
                mAdapterComplete.setList(mList)
            }

        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                e.message?.let { showToast(it) }
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_guard_late_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val menuItem = menu.findItem(R.id.fragmentGuardLate_search)
        mSearchView = menuItem.actionView as SearchView
        searching(mSearchView)
        super.onPrepareOptionsMenu(menu)
    }

    private fun searching(search: SearchView) {
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                mAdapterComplete.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                mAdapterComplete.filter.filter(newText)
                Log.d(TAG, "onQueryTextChange: + $newText")
                return true
            }
        })
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
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        REPOSITORY_ROOM.deleteGuardLate(guard)
                        withContext(Dispatchers.Main) {
                            mAdapterComplete.removeItem(viewHolder)
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            e.message?.let { showToast(it) }
                        }
                    }
                }

                Snackbar.make(
                    viewHolder.itemView, "${guard.guardName} удален",
                    Snackbar.LENGTH_LONG
                ).setActionTextColor(Color.RED)
                    .setAction("Отмена") {

                        CoroutineScope(Dispatchers.IO).launch {
                            try {
                                REPOSITORY_ROOM.insertGuard(guard)
                                withContext(Dispatchers.Main) {
                                    mAdapterComplete.insertItem(removedPosition, guard)
                                    mRecyclerView.smoothScrollToPosition(removedPosition)
                                }
                            } catch (e: Exception) {
                                withContext(Dispatchers.Main) {
                                    e.message?.let { showToast(it) }
                                }
                            }
                        }
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
                    deleteIcon.setBounds(
                        itemView.left + iconMargin, itemView.top + iconMargin,
                        itemView.left + iconMargin + deleteIcon.intrinsicWidth,
                        itemView.bottom - iconMargin
                    )

                } else {
                    swipeBackground.setBounds(
                        itemView.right + dX.toInt(),
                        itemView.top, itemView.right, itemView.bottom
                    )
                    deleteIcon.setBounds(
                        itemView.right - iconMargin - deleteIcon.intrinsicWidth,
                        itemView.top + iconMargin,
                        itemView.right - iconMargin,
                        itemView.bottom - iconMargin
                    )
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
        mRecyclerView.adapter = null
    }

    companion object {
        fun itemClick(guard: Guards) {
            val bundle = Bundle()
            bundle.putSerializable("guard", guard)
            APP_ACTIVITY.navController
                .navigate(R.id.action_fragmentGuardLate_to_fragmentGuardLateByName, bundle)
        }
    }
}

