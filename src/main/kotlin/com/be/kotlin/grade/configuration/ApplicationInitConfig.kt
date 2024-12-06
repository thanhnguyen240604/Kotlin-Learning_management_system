package com.be.kotlin.grade.configuration

import com.be.kotlin.grade.model.User
import com.be.kotlin.grade.repository.UserRepository
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.logging.Logger

@Configuration
class ApplicationInitConfig(
    private val passwordEncoder: PasswordEncoder,
    private val userRepository: UserRepository,
) {

    private val log = Logger.getLogger(ApplicationInitConfig::class.java.name)

    @Bean
    fun applicationRunner(): ApplicationRunner = ApplicationRunner {
        if (userRepository.findByUsername("admin").isEmpty) {
            val user = User(
                username = "admin",
                password = passwordEncoder.encode("admin"),
                role = "ADMIN"
            )
            userRepository.save(user)
            log.warning("Admin user created")
        }
    }
}