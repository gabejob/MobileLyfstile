package com.example.lyfstile

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData


class ViewModel : AndroidViewModel {
    private val userData: MutableList<User> = mutableListOf<User>()
    private var mainWeatherRepository: WeatherRepository ?= null
    private var mainUserRepository: UserRepository ?= null
    private var dataList : HashMap<String, Data> ?= null
    var user : User ?= null

    constructor(application: Application) : super(application) {
        mainWeatherRepository =  application.let { WeatherRepository.getInstance(it) }
        mainUserRepository =  application.let { UserRepository.getInstance(it) }
        user = User()
    }

    fun getFromDataList(dataList : HashMap<String, Data>)
    {
        if (dataList[EMAIL] !== null)
            user?.email = dataList[EMAIL]?.data.toString()
        if (dataList[PASSWORD] !== null)
            user?.password = dataList[PASSWORD]?.data.toString()
        if (dataList[FIRST_NAME] !== null)
            user?.firstName = dataList[FIRST_NAME]?.data.toString()
        if (dataList[LAST_NAME] !== null)
            user?.lastName = dataList[LAST_NAME]?.data.toString()
        if (dataList[AGE] !== null)
            user?.birthday = dataList[AGE]?.data.toString()
        if (dataList[HEIGHT] !== null)
            user?.height = dataList[HEIGHT]?.data.toString()
        if (dataList[WEIGHT] !== null)
            user?.weight = dataList[WEIGHT]?.data.toString()
        if (dataList[SEX] !== null)
            user?.sex = dataList[SEX]?.data.toString()
        if (dataList[COUNTRY] !== null)
            user?.country = dataList[COUNTRY]?.data.toString()
        if (dataList[CITY] !== null)
            user?.city = dataList[CITY]?.data.toString()
        if (dataList[PROFILE_PIC] !== null)
            user?.pfp = dataList[PROFILE_PIC]?.data as Bitmap
    }
    fun setCurrentUser(newUser : User?)
    {
        this.user = newUser
    }

    fun getCurrentUser() : HashMap<String, Data>?
    {
        dataList = HashMap<String, Data>()
        dataList!![EMAIL] = Data(EMAIL,user?.email.toString())
        dataList!![PASSWORD] = Data(PASSWORD,user?.password.toString())
        dataList!![AGE] = Data(AGE,user?.birthday.toString())
        dataList!![SEX] = Data(SEX,user?.sex.toString())
        dataList!![HEIGHT] = Data(HEIGHT,user?.height.toString())
        dataList!![WEIGHT] = Data(WEIGHT,user?.weight.toString())
        dataList!![COUNTRY] = Data(COUNTRY,user?.country.toString())
        dataList!![CITY] = Data(CITY,user?.city.toString())
        dataList!![PROFILE_PIC] = Data(PROFILE_PIC,user?.pfp)


        return dataList
    }

   fun sendToRepository(){}

    //Retrieved after user is in db?
/*    val data: MutableLiveData<User>?
        get() = userData
    init {
        userData = mainUserRepository.get()
    }*/
}

