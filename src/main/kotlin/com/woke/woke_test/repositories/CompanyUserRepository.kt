package com.woke.woke_test.repositories

import com.woke.woke_test.models.CompanyUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CompanyUserRepository : JpaRepository<CompanyUser, Long> {
    fun findByUserId(id: Long): Iterable<CompanyUser>
}