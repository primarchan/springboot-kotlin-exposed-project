package org.example.springbootkotlinexposedproject.domain.user.mapper

import org.jetbrains.exposed.sql.ResultRow
import org.example.springbootkotlinexposedproject.domain.post.dto.PostResponse
import org.example.springbootkotlinexposedproject.domain.user.dto.UserDomain
import org.example.springbootkotlinexposedproject.domain.user.dto.UserResponse
import org.example.springbootkotlinexposedproject.domain.user.dto.UserWithPostsResponse
import org.example.springbootkotlinexposedproject.domain.user.table.User

fun ResultRow.toUserDomain(): UserDomain {
    return UserDomain(
        userId = this[User.id],
        userName = this[User.userName],
        userEmail = this[User.userEmail],
        createdAt = this[User.createdAt].toString(),
        updatedAt = this[User.updatedAt].toString()
    )
}

fun UserDomain.toUserResponse(): UserResponse {
    return UserResponse(
        userId = this.userId,
        userName = this.userName,
        userEmail = this.userEmail,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
    )
}

fun UserResponse.toUserWithPostsResponse(posts: List<PostResponse>): UserWithPostsResponse {
    return UserWithPostsResponse(
        userId = this.userId,
        userName = this.userName,
        userEmail = this.userEmail,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        posts = posts
    )
}