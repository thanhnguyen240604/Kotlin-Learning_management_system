package com.be.kotlin.grade

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GradeApplication

fun main(args: Array<String>) {
	runApplication<GradeApplication>(*args)
}
