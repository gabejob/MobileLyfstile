package com.example.lyfstile

import android.os.Parcel
import org.junit.Assert
import org.junit.Test

class DataTest {
    @Test
    fun CreateDataObject() {
        val u = User(
            "James",
            "Murray",
            "JM@JM.com",
            "cooldude1",
            "12/17",
            "M",
            "5'11",
            "195",
            "USA",
            "Seattle",
            ""
        )
        val d = Data("word", u.toString())
        var rslt = d.data.toString()
        Assert.assertEquals(u, rslt)
/*        Assert.assertEquals(rslt.firstName, u.firstName)
        Assert.assertEquals(rslt.lastName, u.lastName)
        Assert.assertEquals(rslt.email, u.email)
        Assert.assertEquals(rslt.password, u.password)
        Assert.assertEquals(rslt.birthday, u.birthday)
        Assert.assertEquals(rslt.sex, u.sex)
        Assert.assertEquals(rslt.height, u.height)
        Assert.assertEquals(rslt.weight, u.weight)
        Assert.assertEquals(rslt.country, u.country)
        Assert.assertEquals(rslt.city, u.city)
        Assert.assertEquals(rslt.pfp, u.pfp)*/
    }
}