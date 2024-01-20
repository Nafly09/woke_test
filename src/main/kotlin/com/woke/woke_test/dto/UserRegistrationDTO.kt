package com.woke.woke_test.dto

data class UserRegistrationDto(
        val name: String,
        val email: String,
        val birthday: String,
        val phone: Long,
        val password: String
)