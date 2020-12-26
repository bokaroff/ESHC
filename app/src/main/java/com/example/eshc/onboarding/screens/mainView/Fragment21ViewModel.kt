package com.example.eshc.onboarding.screens.mainView

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.eshc.utilits.REPOSITORY_ROOM

class Fragment21ViewModel(application: Application): AndroidViewModel(application) {

    val mainItemList21 = REPOSITORY_ROOM.mainItemList21
}