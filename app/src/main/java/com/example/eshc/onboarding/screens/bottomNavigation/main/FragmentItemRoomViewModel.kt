package com.example.eshc.onboarding.screens.bottomNavigation.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.eshc.utilits.REPOSITORY_ROOM

class FragmentItemRoomViewModel(application: Application) : AndroidViewModel(application) {

    val allChangedItems = REPOSITORY_ROOM.allChangedItems
}