package org.example.springbootkotlinexposedproject.domain.post.dto

data class PostDomain(
    val postId: Long,
    val userId: Long,
    val title: String,
    val content: String,
    val createdAt: String,
    val updatedAt: String
)
