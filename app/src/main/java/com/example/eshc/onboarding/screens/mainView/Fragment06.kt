package com.example.eshc.onboarding.screens.mainView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.eshc.adapters.AdapterItems
import com.example.eshc.databinding.Fragment06Binding
import com.example.eshc.model.Items
import com.example.eshc.utilits.*
import com.google.firebase.firestore.DocumentChange
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class Fragment06 : Fragment() {

    private var _binding: Fragment06Binding? = null
    private val mBinding get() = _binding!!
    private var mMutableList = mutableListOf<Items>()
    private var currentDate: String = String()
    private var currentTime: Date = Date()
    private var timeStartLongType: Long = 0
    private var timeEndLongType: Long = 0

    private lateinit var mDeferred: Deferred<MutableList<Items>>
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapterItems: AdapterItems

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     getData06()
    }

    private fun getData06() {

        mDeferred = CoroutineScope(Dispatchers.IO).async {
            try {
                val list = async { REPOSITORY_ROOM.getMainItemList06() }
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
        // Inflate the layout for this fragment
        _binding = Fragment06Binding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialise()
        setCurrentTime()
        setListToAdapter()
        if (timeRange06) {
            getChanges()
        }
    }

    private fun initialise() {
        mRecyclerView = mBinding.rvFragment06
        mAdapterItems = AdapterItems()
        mRecyclerView.adapter = mAdapterItems
    }

    private fun setCurrentTime() {
        currentDate = SimpleDateFormat("HH:mm, dd/MM/yyyy", Locale.getDefault())
            .format(Date())
        currentTime = Calendar.getInstance(Locale.getDefault()).time

        timeStart06.set(Calendar.HOUR_OF_DAY, 5)
        timeStart06.set(Calendar.MINUTE, 40)
        timeStart06.set(Calendar.SECOND, 0)
        timeEnd06.set(Calendar.HOUR_OF_DAY, 6)
        timeEnd06.set(Calendar.MINUTE, 30)
        timeEnd06.set(Calendar.SECOND, 0)

        timeRange06 = (currentTime.after(timeStart06.time)) && (currentTime.before(timeEnd06.time))
        timeStartLongType = timeStart06.time.time
        timeEndLongType = timeEnd06.time.time
    }

    private fun setListToAdapter() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                mMutableList = mDeferred.await()

                if (timeRange06) {

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
            .whereEqualTo(field_06, "true")
            .addSnapshotListener { value, error ->
                if (value != null) {
                    for (dc in value.documentChanges) {
                        if (dc.type == DocumentChange.Type.MODIFIED) {

                            val currentTimeLongType = currentTime.time
                            val stringTime = SimpleDateFormat(
                                "HH:mm, dd MMM.yyyy",
                                Locale.getDefault()
                            ).format(Date())

                            val item = dc.document.toObject(Items::class.java)
                            val name = item.objectName
                            item.itemLongTime = currentTimeLongType
                            item.serverTimeStamp = stringTime
                            item.state = stateChanged

                            val newIterator: MutableIterator<Items> = mMutableList.iterator()
                            while (newIterator.hasNext()) {
                                val it = newIterator.next()
                                if (it.objectName == name) {
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
        _binding = null
        mRecyclerView.adapter = null
    }
}