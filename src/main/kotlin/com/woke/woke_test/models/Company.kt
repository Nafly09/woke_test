package com.woke.woke_test.models

import jakarta.persistence.*
import javax.validation.constraints.Email

@Entity
@Table(name = "Company")
data class Company (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        @Column(nullable = false)
        val name: String,

        @Column(nullable = false, unique = true)
        @Email
        val companyEmail: String,
)