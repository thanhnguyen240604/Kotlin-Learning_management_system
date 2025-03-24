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
		System.setProperty("SPECIAL_USERS", dotenv["SPECIAL_USERS"] ?: "")
		System.setProperty("SPRING_MAIL_HOST", dotenv["SPRING_MAIL_HOST"] ?: "")
		System.setProperty("SPRING_MAIL_PORT", dotenv["SPRING_MAIL_PORT"] ?: "")
		System.setProperty("SPRING_MAIL_USERNAME", dotenv["SPRING_MAIL_USERNAME"] ?: "")
		System.setProperty("SPRING_MAIL_PASSWORD", dotenv["SPRING_MAIL_PASSWORD"] ?: "")
		System.setProperty("SPRING_MAIL_PROPERTIES_MAIL_SMTP_AUTH", dotenv["SPRING_MAIL_PROPERTIES_MAIL_SMTP_AUTH"] ?: "")
		System.setProperty("SPRING_MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE", dotenv["SPRING_MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE"] ?: "")
		System.setProperty("SPRING_MAIL_PROPERTIES_MAIL_DEBUG", dotenv["SPRING_MAIL_PROPERTIES_MAIL_DEBUG"] ?: "")
		System.setProperty("SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GOOGLE_CLIENT_ID", dotenv["SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GOOGLE_CLIENT_ID"] ?: "")
		System.setProperty("SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GOOGLE_CLIENT_SECRET", dotenv["SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GOOGLE_CLIENT_SECRET"] ?: "")
		System.setProperty("SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GOOGLE_REDIRECT_URI", dotenv["SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GOOGLE_REDIRECT_URI"] ?: "")
		System.setProperty("SPRING_SECURITY_OAUTH2_CLIENT_PROVIDER_GOOGLE_AUTHORIZATION_URI", dotenv["SPRING_SECURITY_OAUTH2_CLIENT_PROVIDER_GOOGLE_AUTHORIZATION_URI"] ?: "")
		System.setProperty("SPRING_SECURITY_OAUTH2_CLIENT_PROVIDER_GOOGLE_TOKEN_URI", dotenv["SPRING_SECURITY_OAUTH2_CLIENT_PROVIDER_GOOGLE_TOKEN_URI"] ?: "")
		System.setProperty("SPRING_SECURITY_OAUTH2_CLIENT_PROVIDER_GOOGLE_USER_INFO_URI", dotenv["SPRING_SECURITY_OAUTH2_CLIENT_PROVIDER_GOOGLE_USER_INFO_URI"] ?: "")
	}
//test
	runApplication<GradeApplication>(*args)
}
