package com.be.kotlin.grade

import io.github.cdimascio.dotenv.Dotenv
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication
import java.io.File

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class GradeApplication

fun main(args: Array<String>) {
	val currentWorkingDir = System.getProperty("user.dir")
	val envPath = ".env"
	val envFile = File(envPath)

	if (envFile.exists() && envFile.isFile) {
		val dotenv = Dotenv.configure()
			.load()

		// Thiết lập các biến môi trường cho Spring Boot
		System.setProperty("SPRING_APPLICATION_NAME", dotenv["SPRING_APPLICATION_NAME"] ?: "")
		System.setProperty("SPRING_DATASOURCE_URL", dotenv["SPRING_DATASOURCE_URL"] ?: "")
		System.setProperty("SPRING_DATASOURCE_USERNAME", dotenv["SPRING_DATASOURCE_USERNAME"] ?: "")
		System.setProperty("SPRING_DATASOURCE_PASSWORD", dotenv["SPRING_DATASOURCE_PASSWORD"] ?: "")
		System.setProperty("SPRING_JPA_HIBERNATE_DDL_AUTO", dotenv["SPRING_JPA_HIBERNATE_DDL_AUTO"] ?: "")
		System.setProperty("SPRING_JPA_SHOW_SQL", dotenv["SPRING_JPA_SHOW_SQL"] ?: "")
		System.setProperty("SERVER_PORT", dotenv["SERVER_PORT"] ?: "")
		System.setProperty("SERVER_CONTEXT_PATH", dotenv["SERVER_CONTEXT_PATH"] ?: "")
		System.setProperty("JWT_SIGNER_KEY", dotenv["JWT_SIGNER_KEY"] ?: "")
	}

	runApplication<GradeApplication>(*args)
}
