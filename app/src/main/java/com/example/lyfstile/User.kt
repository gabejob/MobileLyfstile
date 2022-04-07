package com.example.lyfstile

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable

/*
*
*
* MAJOR NOTE: PASSWORDS SHOULD NEVER BE STORED HERE! THIS IS TEMPORARY!
*
* */
class User constructor(
    _firstname: String, _lastname: String, _email: String,
    _password: String, _age: String, _sex: String, _height: String,
    _weight: String, _country: String, _city: String, _pfp: Any
) : Parcelable {


    constructor() : this(
        "", "", "",
        "", "", "", "", "", "", "", Bitmap.createBitmap(1,1,Bitmap.Config.ARGB_8888)
    ) {

    }

    var firstName: String = _firstname
    var lastName: String = _lastname
    var email: String = _email
    var password: String = _password
    var age: String = _age
    var height: String = _height
    var weight: String = _weight
    var country: String = _country
    var city: String = _city
    var pfp: Bitmap = _pfp as Bitmap
    var sex: String = _sex


    constructor (userEntity: UserEntity) : this() {
        firstName = userEntity.fn.toString()
        lastName = userEntity.ln.toString()
        email = userEntity.email.toString()
        password = userEntity.password.toString()
        height = userEntity.height.toString()
        weight = userEntity.weight.toString()
        country = userEntity.country.toString()
        city = userEntity.city.toString()
        sex = userEntity.sex.toString()
        age = userEntity.age.toString()
    }

    constructor(parcel: Parcel) : this() {
        firstName = parcel.readString().toString()
        lastName = parcel.readString().toString()
        email = parcel.readString().toString()
        password = parcel.readString().toString()
        height = parcel.readString().toString()
        weight = parcel.readString().toString()
        country = parcel.readString().toString()
        city = parcel.readString().toString()
        sex = parcel.readString().toString()
        age = parcel.readString().toString()
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
        out?.writeString(age)
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