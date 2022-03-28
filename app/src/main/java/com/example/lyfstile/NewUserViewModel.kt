package com.example.lyfstile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NewUserViewModel : ViewModel() {

    val currentScreen =  MutableLiveData<String>() //"first_last_name"

    init {
        currentScreen.value = FIRST_LAST_NAME_SCREEN
    }
}