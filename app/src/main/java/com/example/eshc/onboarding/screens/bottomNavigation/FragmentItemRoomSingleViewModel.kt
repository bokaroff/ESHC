package com.example.eshc.onboarding.screens.bottomNavigation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.eshc.model.Items
import com.example.eshc.utilits.REPOSITORY_ROOM
import com.example.eshc.utilits.TAG
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FragmentItemRoomSingleViewModel(application: Application) : AndroidViewModel(application) {
/*
    fun getItem(name: String): List<Items> {

        var mList: List<Items> = emptyList()

      var differ =  viewModelScope.async {
          REPOSITORY_ROOM.singleChangedItem(name)
        }

        viewModelScope.async {
            val a = differ.await()
              mList = a
        }


    return mList
    }



 */

}