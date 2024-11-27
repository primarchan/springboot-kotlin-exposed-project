package org.example.springbootkotlinexposedproject.domain.post.mapper

import org.example.springbootkotlinexposedproject.domain.post.dto.PostDomain
import org.example.springbootkotlinexposedproject.domain.post.dto.PostResponse
import org.example.springbootkotlinexposedproject.domain.post.table.Post
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toPostDomain(): PostDomain {
    return PostDomain(
        postId = this[Post.id],
        userId = this[Post.userId],
        title = this[Post.title],
        content = this[Post.content],
        createdAt = this[Post.createdAt].toString(),
        updatedAt = this[Post.updatedAt].toString(),
    )
}

fun PostDomain.toPostResponse(): PostResponse {
    return PostResponse(
        postId = this.postId,
        userId = this.userId,
        title = this.title,
        content = this.content,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}