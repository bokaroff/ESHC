package com.example.eshc.onboarding.screens.bottomNavigation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.eshc.utilits.REPOSITORY

class FragmentGuardLateViewModel(application: Application): AndroidViewModel(application) {

    val allGuardsLate = REPOSITORY.allGuardsLate
}