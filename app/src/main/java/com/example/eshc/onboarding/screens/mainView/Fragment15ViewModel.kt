package com.example.eshc.onboarding.screens.mainView

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.eshc.utilits.REPOSITORY_ROOM

class Fragment15ViewModel(application: Application): AndroidViewModel(application) {

    val mainItemList15 = REPOSITORY_ROOM.mainItemList15
}