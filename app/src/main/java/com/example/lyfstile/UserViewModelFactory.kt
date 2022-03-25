package com.example.lyfstile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class UserViewModelFactory(private val repository: UserRepository): ViewModelProvider.NewInstanceFactory() {

    public fun <T : ViewModel?> createMe(modelClass: Class<T>): T {
        var x = 1
        return LyfViewModel() as T
    }
}