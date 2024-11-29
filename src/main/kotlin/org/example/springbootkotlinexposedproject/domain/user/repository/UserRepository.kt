package org.example.springbootkotlinexposedproject.domain.user.repository

import org.jetbrains.exposed.sql.*
import org.springframework.stereotype.Repository
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.example.springbootkotlinexposedproject.domain.user.table.User
import org.example.springbootkotlinexposedproject.domain.post.table.Post

@Repository
class UserRepository {

    fun findAll(): List<ResultRow> = transaction {
        User.selectAll()
            .toList()
    }

    fun findByUserEmail(userEmail: String): ResultRow? = transaction {
        User.selectAll()
            .where { User.userEmail eq userEmail }
            .singleOrNull()
    }

    fun findById(userId: Long): ResultRow? = transaction {
        User.selectAll()
            .where { User.id eq userId }
            .singleOrNull()
    }

    fun create(userName: String, userEmail: String): ResultRow = transaction {
        User.insertAndGetResultRow {
            it[User.userName] = userName
            it[User.userEmail] = userEmail
            it[User.createdAt] = java.time.LocalDateTime.now()
            it[User.updatedAt] = java.time.LocalDateTime.now()
        }
    }

    fun Table.insertAndGetResultRow(body: Table.(InsertStatement<Number>) -> Unit): ResultRow =
        insert(body).resultedValues
            ?.first()
            ?: throw IllegalStateException("Failed to retrieve inserted row.")

    /**
     * 커서 기반 페이지네이션을 위한 사용자 조회
     * @param lastUserId 이전 페이지의 마지막 ID (nullable)
     * @param size 가져올 데이터 수
     * @return 사용자 데이터 리스트
     */
    fun findAllByCursor(lastUserId: Long? = null, size: Int): List<ResultRow> = transaction {
        when (lastUserId) {
            null -> User.selectAll()
                .orderBy(User.id, SortOrder.ASC)
                .limit(size)
                .toList()

            else -> User.selectAll()
                .where { User.id greater lastUserId }
                .orderBy(User.id, SortOrder.ASC)
                .limit(size)
                .toList()
        }
    }

    fun findUserWithPostsByUserIdUsingJoin(userId: Long): List<ResultRow> = transaction {
        (User innerJoin Post)
            .selectAll()
            .where { User.id eq userId }
            .toList()
    }
}