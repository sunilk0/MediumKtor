package com.application.mediumktor.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get


class UsersRepository(private val client: HttpClient) {

    suspend fun fetchUsers(): List<UserDTOItem> {
        return client.get("https://jsonplaceholder.typicode.com/users")
            .body() // Ktor infers List<UserDTOItem>
    }
}

