package com.woke.woke_test.models

import jakarta.persistence.*

@Entity
@Table(name = "app_user_company")
data class CompanyUser(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "app_user_id")
        val user: AppUser,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "company_id")
        val company: Company
)