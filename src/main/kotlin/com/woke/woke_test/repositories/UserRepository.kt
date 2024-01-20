package com.woke.woke_test.repositories
import com.woke.woke_test.models.AppUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserRepository : JpaRepository<AppUser, String> {
    fun findAppUserByEmail(email: String): Optional<AppUser>
    fun findAppUserById(id: Long): Optional<AppUser>
}
