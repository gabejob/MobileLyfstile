package com.example.lyfstile

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userEntity: UserEntity?)

    @Query("UPDATE user_table SET age = :age, age = :age, sex = :sex," +
            " height = :height, weight = :weight, country = :country, city = :city WHERE email = :email")
    fun updateUser( email: String, age : String, height : String,
                    weight : String, sex : String, country : String, city : String)


    @Query("DELETE FROM user_table WHERE email = :email")
            fun deleteAll(email : String)

    @Query("SELECT * from user_table ORDER BY email ASC")
    fun getAll(): LiveData<List<UserEntity>>
}