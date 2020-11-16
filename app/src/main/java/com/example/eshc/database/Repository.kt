package com.example.eshc.database

import androidx.lifecycle.LiveData
import com.example.eshc.model.Items

interface Repository {

    val AllItems: LiveData<List<Items>>
}