package com.example.lyfstile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


class ViewModel(application: Application) : AndroidViewModel(application) {
    private val userData: MutableLiveData<User>?
    private val mainRepository: Repository = application.let { Repository.getInstance(it) }



    //Retrieved after user is in db?
    val data: MutableLiveData<User>?
        get() = userData
    init {
        userData = mainRepository.get()
    }
}

