package com.be.kotlin.grade

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class GradeApplication

fun main(args: Array<String>) {
	runApplication<GradeApplication>(*args)
}
