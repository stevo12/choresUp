package com.mafia.chores

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mafia.chores.backend.ApiHelper
import com.mafia.chores.data.User
import com.mafia.chores.viewmodels.RegisterLoginUserViewModel

class AddUserViewModelFactory(private val user: User,private val apiHelper: ApiHelper): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterLoginUserViewModel::class.java)){
            return RegisterLoginUserViewModel(user,MainRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}