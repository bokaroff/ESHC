package com.example.eshc.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.eshc.databinding.FragmentViewPagerBinding
import com.example.eshc.onboarding.screens.*
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_view_pager.*


class ViewPagerFragment : Fragment() {
    private var _binding: FragmentViewPagerBinding? = null
    private val mBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentViewPagerBinding.inflate(layoutInflater, container, false)

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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}