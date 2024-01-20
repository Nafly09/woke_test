package com.woke.woke_test.controllers
import com.woke.woke_test.config.JwtUtil
import com.woke.woke_test.dto.CompanyAssignDTO
import com.woke.woke_test.dto.LoginRequest
import com.woke.woke_test.dto.LoginResponse
import com.woke.woke_test.dto.UserRegistrationDto
import com.woke.woke_test.services.CompanyService
import com.woke.woke_test.services.UserService
import jakarta.transaction.Transactional
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
class UserController(
        private val userService: UserService,
        private val companyService: CompanyService,
        private val authenticationManager: AuthenticationManager,
        private val jwtUtil: JwtUtil,
) {

    @PostMapping("/sign_up")
    fun registerUser(@RequestBody userRegistrationDto: UserRegistrationDto): ResponseEntity<Any> {
        val user = userService.registerUser(userRegistrationDto)

        return ResponseEntity.ok(user.parseToResponse(message = "User Created Successfully"))
    }

    @PostMapping("/login")
    fun createAuthenticationToken(@RequestBody request: LoginRequest): ResponseEntity<Any> {
        authenticate(request.email, request.password)

        val userDetails = userService.loadUserByUsername(request.email)
        val token = jwtUtil.generateToken(userDetails)

        return ResponseEntity.ok(LoginResponse(token = token).parseToResponse())
    }

    private fun authenticate(username: String, password: String) {
        try {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))
        }
        catch (e: BadCredentialsException) {
            throw Exception("INVALID_CREDENTIALS", e)
        }
    }

    @GetMapping("/get_user_info")
    fun getUserInfo(@RequestHeader("Authorization") authorizationHeader: String): ResponseEntity<Any> {
        try {
            if (authorizationHeader.isEmpty() || !authorizationHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Authorization header.")
            }
            val token = authorizationHeader.substring(7)

            val id = jwtUtil.getEmailFromToken(token)

            val user = userService.getUserByEmail(id)

            return ResponseEntity.ok(user.parseToResponse("User Information Available"))
        } catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token.")
        }
    }

    @PostMapping("/users/assign_companies")
    fun assignCompaniesToUser(
            @RequestHeader("Authorization") authorizationHeader: String,
            @RequestBody requestBody: CompanyAssignDTO
    ): ResponseEntity<Any> {
        try {
            if (authorizationHeader.isEmpty() || !authorizationHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Authorization header.")
            }
            val token = authorizationHeader.substring(7)

            val email = jwtUtil.getEmailFromToken(token)

            return companyService.assignCompaniesToUser(companyIds = requestBody.companyIds, email=email)
        } catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token.")
        }
    }

    @Transactional()
    @GetMapping("/users/companies")
    fun getCompaniesForUser(@RequestHeader("Authorization") authorizationHeader: String): ResponseEntity<Any> {
        try {
            if (authorizationHeader.isEmpty() || !authorizationHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Authorization header.")
            }
            val token = authorizationHeader.substring(7)

            val email = jwtUtil.getEmailFromToken(token)

            return ResponseEntity.ok(userService.getCompaniesForUser(email = email))
        } catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token.")
        }
    }
}