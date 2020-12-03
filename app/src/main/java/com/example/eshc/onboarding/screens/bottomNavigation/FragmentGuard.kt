package com.example.eshc.onboarding.screens.bottomNavigation

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
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.eshc.R
import com.example.eshc.adapters.AdapterGuard
import com.example.eshc.databinding.FragmentGuardBinding
import com.example.eshc.model.Guards
import com.example.eshc.utilits.APP_ACTIVITY
import com.example.eshc.utilits.TAG
import com.example.eshc.utilits.collectionGUARDS_REF
import com.example.eshc.utilits.showToast
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.Query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class FragmentGuard : Fragment() {

    private var _binding: FragmentGuardBinding? = null
    private val mBinding get() = _binding!!
    private var mList = mutableListOf<Guards>()
    private var swipeBackground = ColorDrawable(Color.RED)

    private lateinit var mToolbar: Toolbar
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mSearchView: SearchView
    private lateinit var mAdapter: AdapterGuard
    private lateinit var deleteIcon: Drawable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGuardBinding.inflate(
            layoutInflater, container,
            false
        )
        setHasOptionsMenu(true)
        mToolbar = mBinding.fragmentGuardToolbar
        APP_ACTIVITY.setSupportActionBar(mToolbar)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialization()
        getGuardData()
        swipeToDelete()
    }

    private fun initialization() {
        mAdapter = AdapterGuard()
        deleteIcon = ResourcesCompat.getDrawable(
            resources,
            R.drawable.ic_delete_white, null
        )!!
        mRecyclerView = mBinding.rvFragmentGuard
        mToolbar.setupWithNavController(findNavController())
        mRecyclerView.adapter = mAdapter
    }


    private fun getGuardData() = CoroutineScope(Dispatchers.IO).launch {
        val list = mutableListOf<Guards>()
        try {
            val query = collectionGUARDS_REF
                .orderBy("guardName", Query.Direction.ASCENDING)
                .get().await()
            for (dc in query) {
                val guard = dc.toObject(Guards::class.java)
                list.add(guard)
            }
            withContext(Dispatchers.Main) {
                mAdapter.setList(list)
            }

        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                showToast(e.message.toString())
            }
        }
        mList = list
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_guard_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val menuItem = menu.findItem(R.id.fragmentGuard_search)
        mSearchView = menuItem.actionView as SearchView
        searching(mSearchView)
        super.onPrepareOptionsMenu(menu)
    }

    private fun searching(search: SearchView) {
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                mAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                mAdapter.filter.filter(newText)
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
                val id = guard.firestore_id
                Log.d(TAG, "onSwp: $id")


                //     mViewModel.deleteGuardLate(guard)
                //   mViewModel.allGuardsLate.removeObserver(mObserveList)
                mAdapter.removeItem(viewHolder)

                Snackbar.make(
                    viewHolder.itemView, "${guard.guardName} удален",
                    Snackbar.LENGTH_LONG
                ).setActionTextColor(Color.RED)
                    .setAction("Отмена") {
                        // mViewModel.insertGuardLate(guard)
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
}