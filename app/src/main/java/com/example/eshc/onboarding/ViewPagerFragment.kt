package com.example.eshc.onboarding

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


class ViewPagerFragment : Fragment() {
    private var _binding: FragmentViewPagerBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var mAdapterItems: AdapterItems


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     //   insertMainItemsList()
       // insertMainGuardsList()
        Log.d(TAG, "onCreate: ${javaClass.name}")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentViewPagerBinding.inflate(layoutInflater, container, false)

      //  insertMainItemsList()
      //  insertMainGuardsList()
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
        //mAdapterItems = AdapterItems()
       // insertMainItemsList()
        //mAdapterItems.notifyDataSetChanged()
      //  insertMainGuardsList()
        Log.d(TAG, "start: ${javaClass.name}")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "stop: $javaClass")
    }


    private fun insertMainItemsList() {
        val itemsList = mutableListOf<Items>()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val itemsData =  REPOSITORY_ROOM.getMainItemList()
                Log.d(TAG, " getMainItemList_viewpager: + ${itemsData.size}")

                if (itemsData.isEmpty()){

                    val querySnapshot = collectionITEMS_REF
                        .orderBy("objectName", Query.Direction.ASCENDING)
                        .get().await()

                    for (documentSnapShot in querySnapshot) {
                        val item = documentSnapShot.toObject(Items::class.java)
                        item.item_id = documentSnapShot.id
                        itemsList.add(item)
                    }
                    REPOSITORY_ROOM.insertItemList(itemsList)
                    Log.d(TAG, " insertItemList_viewpager: + ${itemsList.size} + ")
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    e.message?.let { showToast(it) }
                }
            }
        }

    }

    private fun insertMainGuardsList() {
        val guardsList = mutableListOf<Guards>()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val guardsData =  REPOSITORY_ROOM.getMainGuardList()
                Log.d(TAG, " getMainGuardList_viewpager: + ${guardsData.size}")

                if (guardsData.isEmpty()){

                    val querySnapshot =  collectionGUARDS_REF
                        .orderBy("guardName", Query.Direction.ASCENDING).get().await()

                    for (documentSnapShot in querySnapshot) {
                        val guard = documentSnapShot.toObject(Guards::class.java)
                        guard.guardFire_id = documentSnapShot.id
                        guardsList.add(guard)
                    }
                    REPOSITORY_ROOM.insertGuardList(guardsList)
                    Log.d(TAG, "insertGuardList_viewpager: + ${guardsList.size} + ")
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    e.message?.let { showToast(it) }
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}