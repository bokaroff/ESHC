package com.example.eshc.onboarding.screens.bottomNavigation.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.eshc.model.Guards
import com.example.eshc.utilits.REPOSITORY_ROOM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentGuardLateViewModel(application: Application): AndroidViewModel(application) {

    val allGuardsLate = REPOSITORY_ROOM.allGuardsLate

    fun deleteGuardLate(guard: Guards) =
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY_ROOM.deleteGuardLate(guard)
        }

    fun insertGuard(guard: Guards) =
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY_ROOM.insertGuard(guard)
        }
}