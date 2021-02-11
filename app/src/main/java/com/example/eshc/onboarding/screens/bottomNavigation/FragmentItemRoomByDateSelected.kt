package com.example.eshc.onboarding.screens.bottomNavigation

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.eshc.adapters.AdapterItemsRoom
import com.example.eshc.databinding.FragmentItemRoomBottomSheetBinding
import com.example.eshc.utilits.APP_ACTIVITY
import com.example.eshc.utilits.REPOSITORY_ROOM
import com.example.eshc.utilits.showToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.DateFormat
import java.util.*

class FragmentItemRoomByDateSelected : Fragment() {
    private var _binding: FragmentItemRoomBottomSheetBinding? = null
    private val mBinding get() = _binding!!

    private var day = 0
    private var month = 0
    private var year = 0
    private var firstDateLongType: Long = 0
    private var secondDateLongType: Long = 0
    private var firstCurrentDateString = ""
    private var secondCurrentDateString = ""

    private lateinit var firstDatePicker: DatePickerDialog
    private lateinit var secondDatePicker: DatePickerDialog
    private lateinit var firstDate: TextView
    private lateinit var secondDate: TextView
    private lateinit var btn: Button
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapterItems: AdapterItemsRoom

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemRoomBottomSheetBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialise()
        getDateCalendar()
        setFirstDate()
        setSecondDate()
        getSelectedData()
    }

    private fun initialise() {
        firstDate = mBinding.fragmentItemRoomBottomSheetEt1
        secondDate = mBinding.fragmentItemRoomBottomSheetEt2
        btn = mBinding.fragmentItemRoomBottomSheetButton
        mRecyclerView = mBinding.rvFragmentItemRoomBottomSheet
        mAdapterItems = AdapterItemsRoom()
        mRecyclerView.adapter = mAdapterItems
    }

    private fun getDateCalendar() {
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
    }

    private fun setFirstDate() {
        firstDate.setOnClickListener {
            firstDatePicker = DatePickerDialog(
                APP_ACTIVITY,
                { _, year, month, dayOfMonth ->
                    val cal = Calendar.getInstance()
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    cal.set(Calendar.MONTH, month)
                    cal.set(Calendar.YEAR, year)

                    firstDateLongType = cal.time.time
                    firstCurrentDateString = DateFormat.getDateInstance().format(cal.time)
                    firstDate.text = firstCurrentDateString
                }, year, month, day
            )
            firstDatePicker.show()
        }
    }

    private fun setSecondDate() {
        secondDate.setOnClickListener {
            secondDatePicker = DatePickerDialog(
                APP_ACTIVITY,
                { _, year, month, dayOfMonth ->
                    val cal = Calendar.getInstance()
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    cal.set(Calendar.MONTH, month)
                    cal.set(Calendar.YEAR, year)

                    secondDateLongType = cal.time.time
                    secondCurrentDateString = DateFormat.getDateInstance().format(cal.time)
                    secondDate.text = secondCurrentDateString
                }, year, month, day
            )
            secondDatePicker.show()
        }
    }

    private fun getSelectedData() {
        btn.setOnClickListener {
            if (firstCurrentDateString.isEmpty() || secondCurrentDateString.isEmpty()) {
                showToast("Заполните все поля")
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val list = REPOSITORY_ROOM
                                .getAllChangedItemsWhereTimeBetween(firstDateLongType, secondDateLongType)
                        withContext(Dispatchers.Main) {
                            mAdapterItems.setList(list)
                            showToast("${list.size}")
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            e.message?.let { showToast(it) }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}