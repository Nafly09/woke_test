package com.woke.woke_test.dto

data class LoginResponse(
        val token: String,
) {
    fun parseToResponse(): Map<String, String> {
        return mapOf("message" to "Login Successful", "access_token" to token)
    }
}

