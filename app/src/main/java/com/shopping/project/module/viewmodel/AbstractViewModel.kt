package com.shopping.project.module.viewmodel

import androidx.annotation.CallSuper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class AbstractViewModel :ViewModel(), CoroutineScope {

    //coroutine handle
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO


    fun cleanUp(){
        job.cancel()
    }

    //handle loading
    val isDataLoading = MutableLiveData<Boolean>()

    //handle exception
    val exception = MutableLiveData<Throwable>()

    @CallSuper
    override fun onCleared() {
        super.onCleared()
    }

    open fun setLoading(isLoading:Boolean? = true){
        isDataLoading.postValue(isLoading)

        if(isLoading == true){
            exception.postValue( null)
        }
    }

    open fun setError(t:Throwable){
        exception.postValue(t)
    }
}