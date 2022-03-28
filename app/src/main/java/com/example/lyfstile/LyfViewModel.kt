package com.example.lyfstile

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


public class LyfViewModel : ViewModel() {

    val user = MutableLiveData<User>()
    var test = MutableLiveData<Int>()
    fun insert(context: Context, user : User) = GlobalScope.launch {
        UserRepository.insert(context,user)
    }

    fun update(context: Context, user : User) = GlobalScope.launch {
        UserRepository.update(context,user)
    }

    var data : LiveData<List<UserEntity>>? =null
    fun allUsers(context : Context) : LiveData<List<UserEntity>>? {
       data = UserRepository.allUsers(context)
        return data
    }

    fun updateContained( newval : Int)
    {
        test.value = newval
    }

    fun contained(context: Context) : LiveData<Int> {
        return test
    }

}
