package com.example.eshc.onboarding.screens.mainView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.eshc.adapters.AdapterItems
import com.example.eshc.databinding.Fragment02Binding
import com.example.eshc.model.Items
import com.example.eshc.utilits.*
import com.google.firebase.firestore.DocumentChange
import kotlinx.coroutines.*
import java.util.*


class Fragment02 : Fragment() {

    private var timeStart02: Calendar = Calendar.getInstance(Locale.getDefault())
    private var timeEnd02: Calendar = Calendar.getInstance(Locale.getDefault())
    private var timeRange02: Boolean = false

    private var _binding: Fragment02Binding? = null
    private val mBinding get() = _binding!!
    private var mMutableList = mutableListOf<Items>()
    private var currentTime: Date = Date()
    private var timeStartLongType: Long = 0
    private var timeEndLongType: Long = 0

    private lateinit var mDeferred: Deferred<MutableList<Items>>
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapterItems: AdapterItems

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getData02()
    }

    private fun getData02() {

        mDeferred = CoroutineScope(Dispatchers.IO).async {
            try {
                val list = async { REPOSITORY_ROOM.getMainItemList02() }
                mMutableList = list.await() as MutableList<Items>
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    e.message?.let { showToast(it) }
                }
            }
            mMutableList
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = Fragment02Binding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialise()
        setCurrentTime()
        setListToAdapter()
        if (timeRange02) {
            getChanges()
        }
    }

    private fun initialise() {
        mRecyclerView = mBinding.rvFragment02
        mAdapterItems = AdapterItems()
        mRecyclerView.adapter = mAdapterItems
    }

    private fun setCurrentTime() {
        currentTime = Calendar.getInstance(Locale.getDefault()).time

        timeStart02.set(Calendar.HOUR_OF_DAY, 1)
        timeStart02.set(Calendar.MINUTE, 40)
        timeStart02.set(Calendar.SECOND, 0)
        timeEnd02.set(Calendar.HOUR_OF_DAY, 2)
        timeEnd02.set(Calendar.MINUTE, 30)
        timeEnd02.set(Calendar.SECOND, 0)

        if ((currentTime.after(timeStart02.time)) && (currentTime.before(timeEnd02.time))) {
            timeRange02 = true
        }
        timeStartLongType = timeStart02.time.time
        timeEndLongType = timeEnd02.time.time
    }

    private fun setListToAdapter() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                mMutableList = mDeferred.await()

                if (timeRange02) {

                    val list = REPOSITORY_ROOM
                        .getAllChangedItemsWhereTimeBetween(timeStartLongType, timeEndLongType)

                    for (item in list) {
                        val name = item.objectName
                        val newIterator: MutableIterator<Items> = mMutableList.iterator()

                        while (newIterator.hasNext()) {
                            val it = newIterator.next()
                            if (it.objectName == name) {
                                newIterator.remove()
                            }
                        }
                    }
                }

                withContext(Dispatchers.Main) {
                    mAdapterItems.setList(mMutableList)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    e.message?.let { showToast(it) }
                }
            }
        }
    }

    private fun getChanges() {
        collectionITEMS_REF
            .whereEqualTo(field_02, "true")
            .addSnapshotListener { value, error ->
                if (value != null) {
                    for (dc in value.documentChanges) {
                        if (dc.type == DocumentChange.Type.MODIFIED) {

                            val currentTimeLongType = currentTime.time

                            val item = dc.document.toObject(Items::class.java)
                            val name = item.objectName
                            item.itemLongTime = currentTimeLongType

                            val newIterator: MutableIterator<Items> = mMutableList.iterator()
                            while (newIterator.hasNext()) {
                                val it = newIterator.next()
                                if (it.objectName == name) {
                                    item.state = stateChanged
                                    saveChangedItemToRoom(item)
                                    val index: Int = mMutableList.lastIndexOf(it)
                                    newIterator.remove()
                                    mAdapterItems.removeItem(index, it, mMutableList)
                                }
                            }
                        }
                    }
                } else showToast(error?.message.toString())
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mRecyclerView.adapter = null
        _binding = null
    }
}