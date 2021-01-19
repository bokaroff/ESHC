package com.example.eshc.onboarding.screens.mainView

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.eshc.model.Items
import com.example.eshc.utilits.REPOSITORY_ROOM
import com.example.eshc.utilits.collectionITEMS_REF
import com.example.eshc.utilits.item_address
import com.example.eshc.utilits.showToast
import com.google.firebase.firestore.DocumentChange

class Fragment08ViewModel(application: Application): AndroidViewModel(application) {

     val mainItemList08 = REPOSITORY_ROOM.mainItemList08


     val list = mainItemList08.value?.toMutableList()







}

