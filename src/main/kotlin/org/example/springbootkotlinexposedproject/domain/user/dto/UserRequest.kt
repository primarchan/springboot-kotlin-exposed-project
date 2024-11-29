package org.example.springbootkotlinexposedproject.domain.user.dto

import jakarta.validation.constraints.Size
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.fasterxml.jackson.databind.PropertyNamingStrategies

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UserRequest(

    @field:NotBlank(message = "Username cannot be blank")
    @field:Size(max = 50, message = "User name must not exceed 50 characters")
    val userName: String,

    @field:NotBlank(message = "Email must not be blank")
    @field:Email(message = "Email must be a valid email address")
    @field:Size(max = 100, message = "Email must not exceed 100 characters")
    val userEmail: String
)