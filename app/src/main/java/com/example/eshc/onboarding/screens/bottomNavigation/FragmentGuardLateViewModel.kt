package com.example.eshc.onboarding.screens.bottomNavigation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.eshc.model.Guards
import com.example.eshc.utilits.REPOSITORY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentGuardLateViewModel(application: Application): AndroidViewModel(application) {

    val allGuardsLate = REPOSITORY.allGuardsLate

    fun deleteGuardLate(guard: Guards){
        viewModelScope.launch(Dispatchers.IO){
            REPOSITORY.deleteGuard(guard)
        }
    }

    fun insertGuardLate(guard: Guards){
        viewModelScope.launch(Dispatchers.IO){
            REPOSITORY.insertGuardLate(guard)
        }
    }

}