package com.woke.woke_test.services

import com.woke.woke_test.models.*
import com.woke.woke_test.repositories.CompanyRepository
import com.woke.woke_test.repositories.CompanyUserRepository
import com.woke.woke_test.repositories.UserRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.*


@Service
class CompanyService {
    @Autowired
    private lateinit var companyRepository: CompanyRepository
    @Autowired
    private lateinit var userRepository: UserRepository

    @Transactional
    fun assignCompaniesToUser(companyIds: List<Long>, email: String): ResponseEntity<Any> {
        val user: AppUser = userRepository.findAppUserByEmail(email).orElseThrow { UsernameNotFoundException("User not found") }

        val companiesToAssign = companyRepository.findAllById(companyIds)

        val existingCompanyIds = user.companies.map { it.id }.toSet()

        val newCompanies = companiesToAssign.filterNot { it.id in existingCompanyIds }

        if (newCompanies.isNotEmpty()) {
            user.companies.addAll(newCompanies)
            userRepository.save(user)
        }

        return ResponseEntity.ok(mapOf("message" to "Companies assigned successfully to user."))
    }

    fun getAvailableCompanies(email: String): ResponseEntity<Any> {
        val user: AppUser = userRepository.findAppUserByEmail(email).orElseThrow { UsernameNotFoundException("User not found") }
        val existingCompanyIds = user.companies.map { it.id }.toSet()
        val companies = companyRepository.findAll()
        val newCompanies = companies.filterNot { it.id in existingCompanyIds }
        return ResponseEntity.ok(mapOf("companies" to newCompanies))
    }
}