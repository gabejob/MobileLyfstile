package com.example.lyfstile

import android.app.Application
import android.os.Looper
import androidx.core.os.HandlerCompat
import androidx.lifecycle.MutableLiveData
import org.json.JSONException
import java.net.URL
import java.util.concurrent.Executors

class Repository private constructor(application: Application) {
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
        private var instance: Repository? = null
        @Synchronized
        fun getInstance(application: Application): Repository {
            if (instance == null) {
                instance = Repository(application)
            }
            return instance as Repository
        }
    }

    init {


    }
}