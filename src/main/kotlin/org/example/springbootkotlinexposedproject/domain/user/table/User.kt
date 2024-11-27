package org.example.springbootkotlinexposedproject.domain.user.table

import java.time.LocalDateTime
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.javatime.datetime

object User : Table("users") {
    var id = long("id").autoIncrement()
    val userName = varchar("user_name", 50)
    val userEmail = varchar("user_email", 100).uniqueIndex()
    val createdAt = datetime("created_at").default(LocalDateTime.now())
    val updatedAt = datetime("updated_at").default(LocalDateTime.now())
    override val primaryKey = PrimaryKey(id)
}