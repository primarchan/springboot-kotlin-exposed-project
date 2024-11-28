package org.example.springbootkotlinexposedproject.domain.user.dto

import org.example.springbootkotlinexposedproject.domain.post.dto.PostDomain

data class UserWithPostsDomain(
    val userId: Long,
    val userName: String,
    val userEmail: String,
    val createdAt: String,
    val updatedAt: String,
    val posts: List<PostDomain>
)
