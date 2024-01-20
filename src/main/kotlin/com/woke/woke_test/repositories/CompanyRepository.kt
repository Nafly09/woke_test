package com.woke.woke_test.repositories

import com.woke.woke_test.models.Company
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CompanyRepository : JpaRepository<Company, Long> {
    fun findByCompanyEmail(email: String): Optional<Company>
}