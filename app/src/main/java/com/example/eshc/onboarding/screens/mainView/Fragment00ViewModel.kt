package com.example.eshc.onboarding.screens.mainView

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.eshc.utilits.REPOSITORY_ROOM

class Fragment00ViewModel(application: Application): AndroidViewModel(application) {

    val mainItemList00 = REPOSITORY_ROOM.mainItemList00
}