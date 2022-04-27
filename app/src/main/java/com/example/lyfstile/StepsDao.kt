package com.example.lyfstile

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface StepsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(stepsEntity: StepsEntity?)

    @Query(
        "UPDATE steps_table SET steps= :steps, dX=:dX, dY=:dY,dZ=:dZ WHERE email = :email"
    )
    fun updateSteps(
        email: String,
        steps: String,
        dX : String,
        dY : String,
        dZ : String,

        //steps: Int
    )

    @Query("DELETE FROM steps_table WHERE email = :email")
    fun deleteAll(email: String)

    @Query("SELECT * from steps_table ORDER BY email ASC")
    fun getAll(): LiveData<List<StepsEntity>>

    @Query("SELECT * from steps_table WHERE email = :email")
    fun getSteps(email: String): LiveData<StepsEntity>
}