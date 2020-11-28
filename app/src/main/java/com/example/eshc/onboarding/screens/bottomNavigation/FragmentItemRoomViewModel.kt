package com.example.eshc.onboarding.screens.bottomNavigation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.eshc.utilits.REPOSITORY_ROOM

class FragmentItemRoomViewModel(application: Application): AndroidViewModel(application) {

    val allItems = REPOSITORY_ROOM.allItems

}