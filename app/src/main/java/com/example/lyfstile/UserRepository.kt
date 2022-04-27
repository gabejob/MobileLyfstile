package com.example.lyfstile

import android.content.Context
import androidx.lifecycle.LiveData

class UserRepository public constructor(private val db: DBHandler) {
    companion object {
        private var db: DBHandler? = null
        var data: LiveData<List<UserEntity>>? = null
        var stepsData : LiveData<List<StepsEntity>>? = null

        fun insertSteps(context: Context,email: String, steps: String, dX: String, dY: String, dZ: String) {
            db = initializeDB(context)
            db!!.daoSteps().insert(StepsEntity(email,steps,dX, dY, dZ))
        }

        fun updateSteps(context: Context,email: String, steps: String, dX: String, dY: String, dZ: String) {
            db = initializeDB(context)
            db!!.daoSteps().updateSteps(email,steps,dX, dY, dZ)
        }
        fun getSteps(context: Context, email: String): LiveData<StepsEntity>? {
            db = initializeDB(context)
            return db?.daoSteps()?.getSteps(email)
        }

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
                //user.steps,
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
                //, user.steps
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