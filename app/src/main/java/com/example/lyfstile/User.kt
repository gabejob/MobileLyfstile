package com.example.lyfstile

import android.os.Parcel
import android.os.Parcelable
import java.util.*


/*
*
*
* MAJOR NOTE: PASSWORDS SHOULD NEVER BE STORED HERE! THIS IS TEMPORARY!
*
* */
class User constructor(_firstname : String, _lastname : String, _email : String,
           _password : String, _birthday : Date,_sex: String, _height : String,
           _weight : String, _country : String, _city : String, _pfp : Any) : Parcelable {


    constructor() : this("","","",
        "",Date(0),"","","","","","")
    {

    }

    var firstName : String = _firstname
    var lastName : String = _lastname
    var email : String = _email
    var password : String = _password
    var birthday : Date = _birthday
    var height : String = _height
    var weight : String = _weight
    var country : String = _country
    var city :  String = _city
    var pfp : Any = _pfp
    var sex : String = _sex

    constructor(parcel: Parcel) : this(
        _firstname = "", _lastname = "", _email = "",
        _password = "", _birthday = Date(0), _sex = "", _height = "", _weight = "", _country = "", _city = "", _pfp = ""
    ) {
        firstName = parcel.readString().toString()
        lastName = parcel.readString().toString()
        email = parcel.readString().toString()
        password = parcel.readString().toString()
        height = parcel.readString().toString()
        weight = parcel.readString().toString()
        country = parcel.readString().toString()
        city = parcel.readString().toString()
        sex = parcel.readString().toString()
    }


    override fun writeToParcel(out: Parcel?, flags: Int) {
        out?.writeString(firstName)
        out?.writeString(lastName)
        out?.writeString(email)
        out?.writeString(password)
        out?.writeString(height)
        out?.writeString(weight)
        out?.writeString(country)
        out?.writeString(city)
        out?.writeString(sex)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }


}