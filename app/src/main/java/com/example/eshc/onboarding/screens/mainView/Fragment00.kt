package com.example.eshc.onboarding.screens.mainView

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.eshc.adapters.AdapterItems
import com.example.eshc.databinding.Fragment00Binding
import com.example.eshc.model.Items
import com.example.eshc.utilits.*
import com.google.firebase.firestore.DocumentChange
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*


class Fragment00 : Fragment() {

    private var _binding: Fragment00Binding? = null
    private val mBinding get() = _binding!!
    private var mMutableList = mutableListOf<Items>()
    private var currentDate: String = String()
    private var currentTime: Date = Date()
    private var timeStartBeforeMidnight: Calendar = Calendar.getInstance(Locale.getDefault())
    private var timeStartAfterMidnight: Calendar = Calendar.getInstance(Locale.getDefault())
    private var timeEndBeforeMidnight: Calendar = Calendar.getInstance(Locale.getDefault())
    private var timeEndAfterMidnight: Calendar = Calendar.getInstance(Locale.getDefault())
    private var timeStartBeforeMidnightLongType: Long = 0
    private var timeStartAfterMidnightLongType: Long = 0
    private var timeEndBeforeMidnightLongType: Long = 0
    private var timeEndAfterMidnightLongType: Long = 0
    private var timeRangeBeforeMidnight: Boolean = false
    private var timeRangeAfterMidnight: Boolean = false
    private var typeConverter = TypeConverter()

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapterItems: AdapterItems
    private lateinit var mDeferred: Deferred<MutableList<Items>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getData00()
    }

    private fun getData00() {

        mDeferred = CoroutineScope(Dispatchers.IO).async {
            try {
                val list = async { REPOSITORY_ROOM.getMainItemList00() }
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
        _binding = Fragment00Binding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialization()
        setCurrentTime()
        setListToAdapter()
        if (timeRangeBeforeMidnight || timeRangeAfterMidnight) {
            getChanges()
        }
    }

    private fun initialization() {
        mRecyclerView = mBinding.rvFragment00
        mAdapterItems = AdapterItems()
        mRecyclerView.adapter = mAdapterItems
    }

    private fun setCurrentTime() {
        currentDate = SimpleDateFormat("HH:mm, dd/MM/yyyy", Locale.getDefault())
            .format(Date())
        currentTime = Calendar.getInstance(Locale.getDefault()).time

        timeStartBeforeMidnight.set(Calendar.HOUR_OF_DAY, 23)
        timeStartBeforeMidnight.set(Calendar.MINUTE, 40)
        timeStartBeforeMidnight.set(Calendar.SECOND, 0)
        timeEndBeforeMidnight.set(Calendar.HOUR_OF_DAY, 23)
        timeEndBeforeMidnight.set(Calendar.MINUTE, 59)
        timeEndBeforeMidnight.set(Calendar.SECOND, 59)

        timeStartAfterMidnight.set(Calendar.HOUR_OF_DAY, 0)
        timeStartAfterMidnight.set(Calendar.MINUTE, 0)
        timeStartAfterMidnight.set(Calendar.SECOND, 0)
        timeEndAfterMidnight.set(Calendar.HOUR_OF_DAY, 0)
        timeEndAfterMidnight.set(Calendar.MINUTE, 30)
        timeEndAfterMidnight.set(Calendar.SECOND, 0)

      //  timeStartBeforeMidnightLongType = typeConverter.dateToLong(timeStartBeforeMidnight.time)
        timeStartBeforeMidnightLongType = timeStartBeforeMidnight.time.time
        timeEndBeforeMidnightLongType = timeEndBeforeMidnight.time.time

        timeStartAfterMidnightLongType = timeStartAfterMidnight.time.time
        timeEndAfterMidnightLongType = timeEndAfterMidnight.time.time

        timeRangeBeforeMidnight = (currentTime.after(timeStartBeforeMidnight.time))
                && (currentTime.before(timeEndBeforeMidnight.time))

        timeRangeAfterMidnight = (currentTime.after(timeStartAfterMidnight.time))
                && (currentTime.before(timeEndAfterMidnight.time))

    }

    private fun setListToAdapter() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                mMutableList = mDeferred.await()

                if (timeRangeBeforeMidnight) {

                    val list = REPOSITORY_ROOM
                        .getAllChangedItemsWhereTimeBetween(
                            timeStartBeforeMidnightLongType,
                            timeEndBeforeMidnightLongType
                        )

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
                } else if (timeRangeAfterMidnight) {

                    val timeStart = timeEndAfterMidnight
                    timeStart.add(Calendar.MINUTE, -50)
                    val timeStartLong = timeStart.time.time
                    Log.d(TAG, "timeRangeBeforeMidnight:  +  ${timeStart.time}")

                    val list = REPOSITORY_ROOM
                        .getAllChangedItemsWhereTimeBetween(
                            timeStartLong,
                            timeEndAfterMidnightLongType
                        )

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
            .whereEqualTo(field_00, "true")
            .addSnapshotListener { value, error ->
                if (value != null) {
                    for (dc in value.documentChanges) {
                        if (dc.type == DocumentChange.Type.MODIFIED) {

                            val currentTimeLongType = currentTime.time
                            val snapTime = SimpleDateFormat(
                                "HH:mm, dd/MM/yyyy",
                                Locale.getDefault()
                            ).format(Date())

                            val item = dc.document.toObject(Items::class.java)
                            val name = item.objectName
                            item.itemLongTime = currentTimeLongType
                            item.serverTimeStamp = snapTime
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