package com.application.mediumktor.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.mediumktor.data.UsersRepository
import kotlinx.coroutines.launch

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.application.mediumktor.data.UserDTOItem
import com.application.mediumktor.ui.httpClient

class UsersViewModel(
    private val repository: UsersRepository = UsersRepository(httpClient)
) : ViewModel() {

    var uiState by mutableStateOf(UsersUiState())
        private set

    init {
        loadUsers()
    }

    fun loadUsers() {
        uiState = uiState.copy(loading = true, error = null)
        viewModelScope.launch {
            runCatching {
                repository.fetchUsers()
            }.onSuccess { users ->
                uiState = uiState.copy(users = users, loading = false)
            }.onFailure { e ->
                uiState = uiState.copy(error = e.message ?: "Unknown error", loading = false)
            }
        }
    }
}



data class UsersUiState(
    val loading: Boolean = false,
    val users: List<UserDTOItem> = emptyList(),
    val error: String? = null
)

