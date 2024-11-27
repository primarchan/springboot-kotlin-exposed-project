package org.example.springbootkotlinexposedproject.common.exception

enum class GlobalErrorCode(val status: Int, val message: String) {
    BAD_REQUEST(400, "Bad request"),
    VALIDATION_ERROR(400, "Validation error"),
    JSON_PARSE_ERROR(400, "Invalid JSON format"),
    INTERNAL_SERVER_ERROR(500, "Internal server error")
}