package com.example.lyfstile

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class UserRepository public constructor(private val db: DBHandler) {


    companion object {

        private var db: DBHandler? = null
        var data : LiveData<List<UserEntity>>? = null

        fun insert(context: Context, user: User) {
            db = initializeDB(context)

           val insert = UserEntity(
                user?.email, user?.password,null,
                null, null, null, null, null, null, null
            )
           db!!.dao().insert(insert)
        }

        fun update(context: Context, user: User)
        {
            db = initializeDB(context)
            db!!.dao().updateUser(user.email,user.firstName,user.lastName, user.birthday, user.height, user.weight, user.sex, user.country, user.city)
        }


        fun allUsers(context: Context) :LiveData<List<UserEntity>>? {
            db = initializeDB(context)
            data = db?.dao()?.getAll()
            return data
        }

        fun initializeDB(context: Context): DBHandler {
            return DBHandler.getDatabaseClient(context)
        }
    }
}