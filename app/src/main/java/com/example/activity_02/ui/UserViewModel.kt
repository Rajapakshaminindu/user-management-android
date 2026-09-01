package com.example.activity_02.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.activity_02.data.AppDatabase
import com.example.activity_02.data.User
import com.example.activity_02.data.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UserRepository
    val searchQuery = MutableStateFlow("")

    init {
        val userDao = AppDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
    }

    val filteredUsers: StateFlow<List<User>> = repository.allUsers
        .combine(searchQuery) { users, query ->
            if (query.isBlank()) {
                users
            } else {
                users.filter { 
                    it.name.contains(query, ignoreCase = true) || 
                    it.email.contains(query, ignoreCase = true) ||
                    it.phone.contains(query, ignoreCase = true)
                }
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun insert(user: User) = viewModelScope.launch {
        repository.insert(user)
    }

    fun updatePassword(userId: Int, newPassword: String) = viewModelScope.launch {
        repository.updatePassword(userId, newPassword)
    }

    fun delete(user: User) = viewModelScope.launch {
        repository.delete(user)
    }
}
