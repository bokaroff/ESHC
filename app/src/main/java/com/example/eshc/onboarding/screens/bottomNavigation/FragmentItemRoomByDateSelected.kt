package com.example.eshc.onboarding.screens.bottomNavigation

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
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

    private var firstDateLongType: Long = 0
    private var secondDateLongType: Long = 0
    private var firstCurrentDateString = ""
    private var secondCurrentDateString = ""

    private lateinit var firstDateTextView: TextView
    private lateinit var secondDateTextView: TextView
    private lateinit var btn: Button
    private lateinit var mToolbar: Toolbar
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
        setStartDate()
        setEndDate()
        getSelectedData()
    }

    private fun initialise() {
        firstDateTextView = mBinding.FragmentItemRoomByDateSelectedTxt1
        secondDateTextView = mBinding.FragmentItemRoomByDateSelectedTxt2
        btn = mBinding.FragmentItemRoomByDateSelectedButton
        mRecyclerView = mBinding.rvFragmentItemRoomByDateSelected
        mToolbar = mBinding.FragmentItemRoomByDateSelectedToolbar
        mToolbar.setupWithNavController(findNavController())
        mAdapterItems = AdapterItemsRoom()
        mRecyclerView.adapter = mAdapterItems
    }

    private fun setStartDate() {
        firstDateTextView.setOnClickListener {

            val currentDateTime = Calendar.getInstance()
            val startYear = currentDateTime.get(Calendar.YEAR)
            val startMonth = currentDateTime.get(Calendar.MONTH)
            val startDay = currentDateTime.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(
                APP_ACTIVITY,
                { _, year, month, day ->

                    val pickedDateTime = Calendar.getInstance()
                    pickedDateTime.set(Calendar.YEAR, year)
                    pickedDateTime.set(Calendar.MONTH, month)
                    pickedDateTime.set(Calendar.DAY_OF_MONTH, day)
                    pickedDateTime.set(Calendar.HOUR_OF_DAY, 0)
                    pickedDateTime.set(Calendar.MINUTE, 0)
                    pickedDateTime.set(Calendar.SECOND, 0)
                    pickedDateTime.set(Calendar.MILLISECOND, 0)

                    firstDateLongType = pickedDateTime.time.time
                    firstCurrentDateString =
                        DateFormat.getDateInstance().format(pickedDateTime.time)
                    firstDateTextView.text = firstCurrentDateString

                },
                startYear,
                startMonth,
                startDay
            ).show()
        }
    }

    private fun setEndDate() {
        secondDateTextView.setOnClickListener {

            val currentDateTime = Calendar.getInstance()
            val startYear = currentDateTime.get(Calendar.YEAR)
            val startMonth = currentDateTime.get(Calendar.MONTH)
            val startDay = currentDateTime.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(
                APP_ACTIVITY,
                { _, year, month, day ->

                    val pickedDateTime = Calendar.getInstance()
                    pickedDateTime.set(Calendar.YEAR, year)
                    pickedDateTime.set(Calendar.MONTH, month)
                    pickedDateTime.set(Calendar.DAY_OF_MONTH, day)
                    pickedDateTime.set(Calendar.HOUR_OF_DAY, 0)
                    pickedDateTime.set(Calendar.MINUTE, 0)
                    pickedDateTime.set(Calendar.SECOND, 0)
                    pickedDateTime.set(Calendar.MILLISECOND, 0)

                    secondDateLongType = pickedDateTime.time.time
                    secondCurrentDateString =
                        DateFormat.getDateInstance().format(pickedDateTime.time)
                    secondDateTextView.text = secondCurrentDateString

                },
                startYear,
                startMonth,
                startDay
            ).show()
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
                            .getAllChangedItemsWhereTimeBetween(
                                firstDateLongType,
                                secondDateLongType
                            )
                        withContext(Dispatchers.Main) {
                            mAdapterItems.setList(list.asReversed())
                            if (list.isNullOrEmpty()) {
                                showToast("Объектов за данный период нет")
                            }
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
        mRecyclerView.adapter = null
        _binding = null
    }
}