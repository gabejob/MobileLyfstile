package com.example.lyfstile

import android.os.Parcel
import android.os.Parcelable

/**
 *
 * Class representing data to be passed between fragments/activities
 *
 * MUST USE SENDER!
 *
 *
 *
 */
public class Data(_sender: String,_data: Any?) : Parcelable
{

    //This looks weird, might look further into changing it
    constructor(parcel: Parcel) : this(_sender = "", _data = "") {
        sender = parcel.readString().toString()
        data = parcel.readString().toString()
    }

    var sender: String = _sender
    var data : Any? = _data


    override fun describeContents(): Int {
        return 0;
    }

    override fun writeToParcel(out: Parcel?, flags: Int) {
        out?.writeString(sender)
       // out?.writeString(data)
    }

    companion object CREATOR : Parcelable.Creator<Data> {
        override fun createFromParcel(parcel: Parcel): Data {
            return Data(parcel)
        }

        override fun newArray(size: Int): Array<Data?> {
            return arrayOfNulls(size)
        }
    }
}

public interface PassData{
    fun onDataPass(data: Data)
}