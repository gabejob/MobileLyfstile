package com.example.lyfstile

import android.content.Context
import androidx.lifecycle.LiveData

class UserRepository public constructor(private val db: DBHandler) {
    companion object {
        private var db: DBHandler? = null
        var data: LiveData<List<UserEntity>>? = null

        fun insert(context: Context, user: User) {
            db = initializeDB(context)

            val insert = UserEntity(
                user.email,
                user.password,
                user.firstName,
                user.lastName,
                user.age,
                user.sex,
                user.height,
                user.weight,
                user.country,
                user.city,
                user.pfp
            )
            db!!.dao().insert(insert)
        }

        fun update(context: Context, user: User) {
            db = initializeDB(context)
            db!!.dao().updateUser(
                user.email,
                user.firstName,
                user.lastName,
                user.age,
                user.sex,
                user.height,
                user.weight
            )
        }

        fun allUsers(context: Context): LiveData<List<UserEntity>>? {
            db = initializeDB(context)
            data = db?.dao()?.getAll()
            return data
        }

        fun getUser(context: Context, email: String): LiveData<UserEntity>? {
            db = initializeDB(context)
            return db?.dao()?.getUser(email)
        }

        fun initializeDB(context: Context): DBHandler {
            return DBHandler.getDatabaseClient(context)
        }
    }
}