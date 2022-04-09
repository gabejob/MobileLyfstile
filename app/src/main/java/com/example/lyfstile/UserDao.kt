package com.example.lyfstile

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userEntity: UserEntity?)

    @Query(
        "UPDATE user_table SET firstname=:firstname, lastname= :lastname, age = :age, sex = :sex," +
                " height = :height, weight = :weight WHERE email = :email"
    )
    fun updateUser(
        email: String,
        firstname: String,
        lastname: String,
        age: String,
        sex: String,
        height: String,
        weight: String
    )

    @Query("DELETE FROM user_table WHERE email = :email")
    fun deleteAll(email: String)

    @Query("SELECT * from user_table ORDER BY email ASC")
    fun getAll(): LiveData<List<UserEntity>>

    @Query("SELECT * from user_table WHERE email = :email")
    fun getUser(email: String): LiveData<UserEntity>
}