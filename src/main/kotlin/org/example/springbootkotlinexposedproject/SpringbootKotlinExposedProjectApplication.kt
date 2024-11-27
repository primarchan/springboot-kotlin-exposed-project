package org.example.springbootkotlinexposedproject

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringbootKotlinExposedProjectApplication

fun main(args: Array<String>) {
    runApplication<SpringbootKotlinExposedProjectApplication>(*args)
}
