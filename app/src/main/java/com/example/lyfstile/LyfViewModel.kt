package com.example.lyfstile

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


public class LyfViewModel : ViewModel(), MainActivity.Backup {

    var user = User()
    var data: LiveData<List<UserEntity>>? = null
    var context : Context ?= null

    // temporary until we add steps to the database
    var steps: MutableLiveData<Int>? = MutableLiveData()
    fun updateSteps() : MutableLiveData<Int>? {
        return steps
    }
    init{
        steps?.value = 0
    }


 fun insertWeather(context: Context, date:String,temp:String) = GlobalScope.launch {
        WeatherRepo.insert(context, date,temp)
    }
    fun getWeather(context: Context, date:String): LiveData<WeatherEntity>? {
        return WeatherRepo.getWeather(context, date)
    }

    fun insertSteps(context: Context,email: String, steps: String, dX: String, dY: String, dZ: String) = GlobalScope.launch {
        UserRepository.insertSteps(context,email, steps, dX, dY, dZ)
    }

    fun updateSteps(context: Context,email: String, steps: String, dX: String, dY: String, dZ: String) = GlobalScope.launch {
        UserRepository.updateSteps(context,email, steps, dX, dY, dZ)
    }
    fun getSteps(context: Context,email: String): LiveData<StepsEntity>? {
        return UserRepository.getSteps(context, email)
    }



        fun insert(context: Context, user: User) = GlobalScope.launch {
            UserRepository.insert(context, user)
        }

        fun update(context: Context, user: User) = GlobalScope.launch {
            UserRepository.update(context, user)
        }

        fun allUsers(context: Context): LiveData<List<UserEntity>>? {
            data = UserRepository.allUsers(context)
            return data
        }

        fun getUser(context: Context, email: String): LiveData<UserEntity>? {
            return UserRepository.getUser(context, email)
        }

        override fun uploadFile() {
            var main = context as MainActivity
            main.uploadFile()
        }

        override fun downloadDB() {
            var main = context as MainActivity
            main.downloadDB()
        }

        override fun bind(context: Context) {
            this.context = context
        }
    }

