package com.example.lyfstile

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import kotlinx.coroutines.runBlocking

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class DBHandler : RoomDatabase() {

    abstract fun dao(): UserDao

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














/*    @Synchronized
    fun getDatabase(context: Context): DBHandler? {
        if (userdb == null) {
            userdb = Room.databaseBuilder(
                context.applicationContext,
                DBHandler::class.java, "users.db"
            ).addCallback(usersDatabaseCallback).build()
        }
        return userdb
    }

    private val usersDatabaseCallback: Callback = object : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            runBlocking {
                var dao = userdb?.dao()
                dao?.deleteAll()
                val userTable: UserTable =
                    UserTableBuilder().setEmail("dummy_loc").setPassword("dummy_data")
                        .createUserTable()
                dao?.insert(userTable)
            }
        }
    }

    private val usersDatabaseCallback2: Callback = object : Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            userdb?.let {
                PopulateDbTask(it)
                    .execute()
            }
        }
    }

    class PopulateDbTask constructor(db: DBHandler) {
        private final var dao = db.dao()

        fun execute() = runBlocking {
                dao?.deleteAll()
                val weatherTable: UserTable =
                    UserTableBuilder().setEmail("dummy_loc").setPassword("dummy_data")
                        .createUserTable()
                dao?.insert(weatherTable)
        }

    }*/
}





