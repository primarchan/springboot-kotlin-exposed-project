package org.example.springbootkotlinexposedproject.domain.post.repository

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll
import org.springframework.stereotype.Repository
import org.jetbrains.exposed.sql.transactions.transaction
import org.example.springbootkotlinexposedproject.domain.post.table.Post

@Repository
class PostRepository {
    /**
     * 특정 사용자가 작성한 포스트 조회
     * @param userId 사용자 ID
     * @return 사용자 작성 포스트 리스트
     */
    fun findByUserId(userId: Long): List<ResultRow> {
        return transaction {
            Post.selectAll()
                .where { Post.userId eq userId }
                .toList()
        }
    }
}