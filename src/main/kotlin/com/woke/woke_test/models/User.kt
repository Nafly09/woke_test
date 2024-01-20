package com.woke.woke_test.models

import com.woke.woke_test.dto.UserResponseDto
import jakarta.persistence.*
import javax.validation.constraints.Email

@Entity
@Table(name = "app_user")
data class AppUser(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        @Column(nullable = false)
        val name: String,

        @Column(nullable = false, unique = true)
        val phone: Long,

        @Column(nullable = false, unique = true)
        @Email
        val email: String,

        @Column(nullable = false)
        val birthday: String,

        @Column(nullable = false)
        val password: String,


        @JoinTable(
                name = "app_user_company",
                joinColumns = [JoinColumn(name = "app_user_id")],
                inverseJoinColumns = [JoinColumn(name = "company_id")]
        )
        @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.REFRESH])
        val companies: MutableSet<Company> = HashSet()
) {
        fun parseToResponse(message: String): Map<String, Any> {
                val parsedUser = UserResponseDto(
                        id = this.id,
                        name = this.name,
                        email = this.email,
                        birthday = this.birthday,
                        phone = this.phone,
                        companies = this.companies
                )
                return mapOf("message" to message, "user" to parsedUser)
        }
}
