package com.example.lyfstile

import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class UserTest {
/*
    @get:Rule
     val newUser = User("James", "Murray", "JM@JM.com", "cooldude1", "12/17", "M", "5'11", "195", "USA", "Seattle", "")
*/

    // basic test to validate data when object is created
    @Test
    fun verifyData() {
        val newUser = User("James", "Murray", "JM@JM.com", "cooldude1", "12/17", "M", "5'11", "195", "USA", "Seattle", "")

        Assert.assertEquals("James", newUser.firstName)
        Assert.assertEquals("Murray", newUser.lastName)
        Assert.assertEquals("JM@JM.com", newUser.email)
        Assert.assertEquals("cooldude1", newUser.password)
        Assert.assertEquals("12/17", newUser.birthday)
        Assert.assertEquals("M", newUser.sex)
        Assert.assertEquals("5'11", newUser.height)
        Assert.assertEquals("195", newUser.weight)
        Assert.assertEquals("USA", newUser.country)
        Assert.assertEquals("Seattle", newUser.city)
        Assert.assertEquals("", newUser.pfp)

    }
}