package com.example.lyfstile

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import kotlinx.coroutines.runBlocking

@Database(entities = [UserEntity::class,StepsEntity::class], version = 2, exportSchema = false)
abstract class DBHandler : RoomDatabase() {

    abstract fun dao(): UserDao
    abstract fun daoSteps() : StepsDao
    companion object
    {
        @Volatile
        private var instance: DBHandler?= null

        fun getDatabaseClient(context: Context) : DBHandler {

            if (instance != null) return instance!!

            synchronized(this) {

                instance = Room
                    .databaseBuilder(context, DBHandler::class.java, "user_db")
                    .fallbackToDestructiveMigration()
                    .build()
                return instance!!
            }
        }

    }

}





