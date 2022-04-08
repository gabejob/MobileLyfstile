package com.example.lyfstile

import androidx.annotation.NonNull
import androidx.room.*

@Entity(tableName = "user_table")
data class UserEntity constructor(

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "email")
    var email: String,

    @NonNull
    @ColumnInfo(name = "password")
    var password: String?,

    @ColumnInfo(name = "firstname")
    var fn: String?,

    @ColumnInfo(name = "lastname")
    var ln: String?,

    @ColumnInfo(name = "age")
    var age: String?,

    @ColumnInfo(name = "height")
    var height: String?,

    @ColumnInfo(name = "weight")
    var weight: String?,

    @ColumnInfo(name = "sex")
    var sex: String?,

    @ColumnInfo(name = "country")
    var country: String?,

    @ColumnInfo(name = "city")
    var city: String?,

    @ColumnInfo(name = "pfp")
    var pfp: ByteArray
) {
}
