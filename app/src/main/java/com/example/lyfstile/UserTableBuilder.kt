package com.example.lyfstile

class UserTableBuilder {
    private var email : String ?= null
    private var password : String ?= null

    public fun setEmail(email : String?) : UserTableBuilder
    {
        this.email = email
        return this
    }

    fun setPassword(password: String?) : UserTableBuilder
    {
        this.password = password
        return this
    }


    public fun createUserTable(): UserEntity {return UserEntity(email as String, password,
        null,null,null,null,null,null,null, null,
        //0,
        ByteArray(0))}
}