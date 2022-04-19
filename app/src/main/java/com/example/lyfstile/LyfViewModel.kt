package com.example.lyfstile

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


public class LyfViewModel : ViewModel(), MainActivity.Backup {

    var user = User()
    var data: LiveData<List<UserEntity>>? = null
    var context : Context ?= null

    fun insert(context: Context, user: User) = GlobalScope.launch {
        UserRepository.insert(context, user)
    }

    fun update(context: Context, user: User) = GlobalScope.launch {
        UserRepository.update(context, user)
    }

    fun allUsers(context: Context): LiveData<List<UserEntity>>? {
        data = UserRepository.allUsers(context)
        return data
    }

    fun getUser(context: Context, email: String): LiveData<UserEntity>? {
        return UserRepository.getUser(context, email)
    }

    override fun uploadFile() {
      var main = context as MainActivity
        main.uploadFile()
    }

    override fun downloadDB() {
        var main = context as MainActivity
        main.downloadDB()
    }
    override fun bind(context: Context) {
        this.context = context
    }

}
