package org.example.springbootkotlinexposedproject.domain.post.table

import org.example.springbootkotlinexposedproject.domain.user.table.User
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object Post : Table("posts") {
    val id = long("id").autoIncrement()
    val userId = reference("user_id", User.id)
    val title = varchar("title", 100)
    val content = text("content")
    val createdAt = datetime("created_at").default(LocalDateTime.now())
    val updatedAt = datetime("updated_at").default(LocalDateTime.now())
    override val primaryKey = PrimaryKey(id)
}