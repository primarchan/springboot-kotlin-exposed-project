package org.example.springbootkotlinexposedproject.domain.user.exception

enum class UserErrorCode(val status: Int, val message: String) {
    USER_NOT_FOUND(404, "User not found"),
    EMAIL_NOT_FOUND(404, "Email not found"),
    USER_ALREADY_EXISTS(400, "User already exists"),
    INVALID_PAGINATION_REQUEST(400, "Invalid pagination request")
}