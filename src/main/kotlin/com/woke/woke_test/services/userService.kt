package com.woke.woke_test.services

import com.woke.woke_test.models.AppUser
import com.woke.woke_test.dto.UserRegistrationDto
import com.woke.woke_test.models.Company
import com.woke.woke_test.repositories.UserRepository
import org.hibernate.Hibernate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService : UserDetailsService {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    @Lazy
    private lateinit var passwordEncoder: PasswordEncoder

    override fun loadUserByUsername(email: String): UserDetails {
        val user = getUserByEmail(email = email)
        return org.springframework.security.core.userdetails.User
                .withUsername(user.email)
                .password(user.password)
                .build()
    }

    fun getUserByEmail(email: String): AppUser {
        val user = userRepository.findAppUserByEmail(email)
                .orElseThrow { UsernameNotFoundException("User not found") }
        return user
    }

    fun getUserById(id: String): AppUser {
        val user = userRepository.findAppUserById(id.toLong())
                .orElseThrow { UsernameNotFoundException("User not found") }
        return user
    }

    fun registerUser(registrationDto: UserRegistrationDto): AppUser {
        val user = AppUser(
                name = registrationDto.name,
                email = registrationDto.email,
                birthday = registrationDto.birthday,
                phone = registrationDto.phone,
                password = passwordEncoder.encode(registrationDto.password)
        )
        return userRepository.save(user)
    }

    fun getCompaniesForUser(email: String): Set<Company> {
        val user = userRepository.findAppUserByEmail(email)
                .orElseThrow { UsernameNotFoundException("User not found") }
        return user.companies
    }
}
