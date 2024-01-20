package com.woke.woke_test.dto

import com.woke.woke_test.models.Company

data class UserResponseDto(
        val id: Long?,
        val name: String,
        val email: String,
        val birthday: String,
        val phone: Long,
        val companies: Set<Company>
)