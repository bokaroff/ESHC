package com.example.eshc.onboarding.screens.bottomNavigation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.eshc.utilits.REPOSITORY

class FragmentItemRoomViewModel(application: Application): AndroidViewModel(application) {

    val allItems = REPOSITORY.allItems

}