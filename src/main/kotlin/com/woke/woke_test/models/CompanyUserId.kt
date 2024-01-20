package com.woke.woke_test.models

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class CompanyUserId(
        @Column()
        val appUserId: Long = 0,

        @Column()
        val companyId: Long = 0
) : Serializable
