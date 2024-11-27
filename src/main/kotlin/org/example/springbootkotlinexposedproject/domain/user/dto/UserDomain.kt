package org.example.springbootkotlinexposedproject.domain.user.dto

data class UserDomain(
    val userId: Long,
    val userName: String,
    var userEmail: String,
    val createdAt: String,
    val updatedAt: String
)
