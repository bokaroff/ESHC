package com.example.eshc.onboarding.screens

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class Observer : LifecycleObserver{
/*
    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun any(){
        Log.d(TAG, "ON_ANY")
    }


 */


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun destroy(){
        Log.d(TAG, "DESTROY")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun pause(){
        Log.d(TAG, "ON_PAUSE")
    }




    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun create(){
        Log.d(TAG, "ON_CREATE")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun resume(){

        Log.d(TAG, "ON_RESUME")
    }



    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun start(){
        Log.d(TAG, "start")
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stop() {
        Log.d(TAG, "stop")
    }






}