package org.example.springbootkotlinexposedproject.domain.user.exception

class UserServiceException(
    val errorCode: UserErrorCode,
    val additionalInfo: Map<String, Any>? = null
) : RuntimeException(errorCode.message)