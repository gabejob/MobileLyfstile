package com.example.lyfstile

import androidx.annotation.NonNull
import androidx.room.*

@Entity(tableName = "steps_table")
data class StepsEntity constructor(

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "email")
    var email: String,

    @ColumnInfo(name = "steps")
    var steps: String,

    @ColumnInfo(name = "dX")
    var dX: String,
    @ColumnInfo(name = "dY")
    var dY: String,
    @ColumnInfo(name = "dZ")
    var dZ: String,

    ) {
}
