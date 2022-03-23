package com.example.lyfstile

import android.app.Application
import androidx.lifecycle.MutableLiveData

class UserRepository private constructor(application: Application) {
    private val userData: MutableLiveData<User> = MutableLiveData<User>()

    val data: MutableLiveData<User>
        get() = userData

    private fun loadData() {
        // FetchWeatherTask().execute(mLocation)
    }


    fun get(): MutableLiveData<User>? {
        return null
    }

    companion object {
        private var instance: UserRepository? = null
        @Synchronized
        fun getInstance(application: Application): UserRepository {
            if (instance == null) {
                instance = UserRepository(application)
            }
            return instance as UserRepository
        }
    }

    init {


    }
}