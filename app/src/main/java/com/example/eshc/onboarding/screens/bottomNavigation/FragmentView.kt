package com.example.eshc.onboarding.screens.bottomNavigation

import android.app.Activity
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
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.eshc.R
import com.example.eshc.adapters.FireItemAdapter
import com.example.eshc.databinding.FragmentViewBinding
import com.example.eshc.model.Items
import com.example.eshc.utilits.*
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.Query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil

class FragmentView : Fragment() {
    private var _binding: FragmentViewBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mToolbar: Toolbar
    private lateinit var mKey: String
    private lateinit var mViewHolder: RecyclerView.ViewHolder
    private lateinit var deleteIcon: Drawable
    private var swipeBackground = ColorDrawable(Color.RED)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentViewBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getData()
    }

    private fun getData() {
        val query = collectionITEMS_REF
            .orderBy("objectName", Query.Direction.ASCENDING)
        optionsItems = FirestoreRecyclerOptions.Builder<Items>()
            .setQuery(query, Items::class.java)
            .build()
        adapterFireItem = FireItemAdapter(optionsItems)
    }

    override fun onStart() {
        super.onStart()
        initialization()
        swipeToDelete()
        UIUtil.hideKeyboard(context as Activity)
        Log.d(TAG, "start: $javaClass")
    }

    private fun initialization() {
        deleteIcon = ResourcesCompat.getDrawable(
            resources,
            R.drawable.ic_delete_white, null
        )!!
        mRecyclerView = mBinding.rvFragmentView
        mToolbar = mBinding.fragmentViewToolbar
        mToolbar.setupWithNavController(findNavController())
        mRecyclerView.adapter = adapterFireItem
        adapterFireItem.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapterFireItem.stopListening()
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
                mViewHolder = viewHolder
                val removedPosition = viewHolder.adapterPosition
                performSwipe(removedPosition)
                Log.d(TAG, "position: + ${viewHolder.adapterPosition} ")
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

    private fun performSwipe(removedPosition: Int) {
        val item = adapterFireItem.getItem(mViewHolder.adapterPosition)
        val name = item.objectName
        mKey = item.item_id
        Log.d(TAG, "mKey: $mKey + $name ")

        CoroutineScope(Dispatchers.IO).launch {
            try {
                collectionITEMS_REF.document(mKey).delete().await()
                REPOSITORY_ROOM.deleteMainItem(mKey)
                Log.d(TAG, "deleted: + ${item.objectName}")

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    e.message?.let { showToast(it) }
                }
            }
        }

        Snackbar.make(
            mViewHolder.itemView, " Объект $name удален",
            Snackbar.LENGTH_LONG
        ).setActionTextColor(Color.RED)
            .setAction("Отмена") {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        collectionITEMS_REF.document(mKey).set(item).await()
                        collectionITEMS_REF.document(mKey)
                            .update(item_fire_id, mKey).await()
                        mRecyclerView.smoothScrollToPosition(removedPosition)
                        item.state = stateMain
                        REPOSITORY_ROOM.insertMainItem(item)

                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            e.message?.let { showToast(it) }
                        }
                    }
                }
            }.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d(TAG, "stop: $javaClass")
    }

    companion object {
        fun popUpFragmentClick(item: Items) {
            val bundle = Bundle()
            bundle.putSerializable("item", item)
            APP_ACTIVITY.navController
                .navigate(R.id.action_fragmentView_to_fragmentViewBottomSheet, bundle)
        }
    }
}