package com.application.mediumktor.data

import kotlinx.serialization.Serializable

@Serializable
data class UserDTOItem(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val phone: String? = null,
    val website: String? = null,
    val company: Company? = null,
    val address: Address? = null
)

@Serializable
data class Company(
    val name: String? = null,
    val catchPhrase: String? = null,
    val bs: String? = null
)

@Serializable
data class Address(
    val street: String? = null,
    val suite: String? = null,
    val city: String? = null,
    val zipcode: String? = null
)