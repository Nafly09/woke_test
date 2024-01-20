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

        val companies = companyRepository.findAllById(companyIds)

        user.companies.clear()
        user.companies.addAll(companies)
        userRepository.save(user)
        return ResponseEntity.ok().body("Companies assigned successfully to user.")
    }
}