package com.example.eshc.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.eshc.R
import com.example.eshc.onboarding.screens.*
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_view_pager.view.*


class ViewPagerFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_view_pager, container, false)

        val fragmentList = arrayListOf<Fragment>(
            Fragment_08(),
            Fragment_15(),
            Fragment_21(),
            Fragment_00(),
            Fragment_02(),
            Fragment_04(),
            Fragment_06()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )
        view.viewPager.offscreenPageLimit = 7
        view.viewPager.adapter = adapter
        TabLayoutMediator(view.tabLayout, view.viewPager) { tab, position ->
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

        return view
    }


}