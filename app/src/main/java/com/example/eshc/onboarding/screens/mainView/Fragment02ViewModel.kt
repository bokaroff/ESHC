package com.example.eshc.onboarding.screens.mainView

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.eshc.database.room.RoomRepository
import com.example.eshc.utilits.REPOSITORY_ROOM

class Fragment02ViewModel(application: Application): AndroidViewModel(application) {

    val mainItemList02 = REPOSITORY_ROOM.mainItemList02
}