package com.example.eshc.onboarding

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.eshc.adapters.AdapterItems
import com.example.eshc.databinding.FragmentViewPagerBinding
import com.example.eshc.model.Guards
import com.example.eshc.model.Items
import com.example.eshc.onboarding.screens.mainView.*
import com.example.eshc.utilits.*
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.firestore.Query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil


class ViewPagerFragment : Fragment() {
    private var _binding: FragmentViewPagerBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var mAdapterItems: AdapterItems


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ${javaClass.name}")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentViewPagerBinding.inflate(layoutInflater, container, false)
        Log.d(TAG, "onCreateView: ${javaClass.name}")

        val fragmentList = arrayListOf<Fragment>(
            Fragment08(),
            Fragment15(),
            Fragment21(),
            Fragment00(),
            Fragment02(),
            Fragment04(),
            Fragment06()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        mBinding.viewPager.offscreenPageLimit = 7
        mBinding.viewPager.adapter = adapter
        TabLayoutMediator(mBinding.tabLayout, mBinding.viewPager) { tab, position ->
            //tab.text = "${position + 1}"
            when (position) {
                0 -> tab.text = "08:00"
                1 -> tab.text = "15:00"
                2 -> tab.text = "21:00"
                3 -> tab.text = "00:00"
                4 -> tab.text = "02:00"
                5 -> tab.text = "04:00"
                6 -> tab.text = "06:00"
            }
        }.attach()

        setHasOptionsMenu(true)
        return mBinding.root
    }


    override fun onStart() {
        super.onStart()
        UIUtil.hideKeyboard(context as Activity)
        Log.d(TAG, "start: ${javaClass.name}")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "stop: $javaClass")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}