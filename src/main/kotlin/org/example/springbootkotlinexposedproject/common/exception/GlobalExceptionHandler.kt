package org.example.springbootkotlinexposedproject.common.exception

import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.MethodArgumentNotValidException
import org.example.springbootkotlinexposedproject.common.dto.ApiResponse
import org.example.springbootkotlinexposedproject.common.dto.ErrorDetails
import org.springframework.http.converter.HttpMessageNotReadableException
import org.example.springbootkotlinexposedproject.domain.user.exception.UserServiceException

@RestControllerAdvice
class GlobalExceptionHandler {
    /**
     * User 도메인 관련 예외 처리
     */
    @ExceptionHandler(UserServiceException::class)
    fun handleCustomException(e: UserServiceException): ApiResponse<Nothing> {
        val errorCode = e.errorCode
        return ApiResponse.error(
            code = errorCode.status,
            message = errorCode.message,
            errorDetails = ErrorDetails(
                errorCode = errorCode.name,
                additionalInfo = e.additionalInfo
            )
        )
    }

    /**
     * 유효성 검증 실패
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(e: MethodArgumentNotValidException): ApiResponse<Nothing> {
        val errors = e.bindingResult.fieldErrors.associate{ it.field to it.defaultMessage }
        return ApiResponse.error(
            code = GlobalErrorCode.VALIDATION_ERROR.status,
            message = GlobalErrorCode.VALIDATION_ERROR.message,
            errorDetails = ErrorDetails(
                errorCode = GlobalErrorCode.VALIDATION_ERROR.name,
                additionalInfo = mapOf("fieldErrors" to errors)
            )
        )
    }

    /**
     * JSON 파싱 에러 처리
     */
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleJsonParseException(e: HttpMessageNotReadableException): ApiResponse<Nothing> {
        return ApiResponse.error(
            code = GlobalErrorCode.JSON_PARSE_ERROR.status,
            message = GlobalErrorCode.JSON_PARSE_ERROR.message,
            errorDetails = ErrorDetails(
                errorCode = GlobalErrorCode.JSON_PARSE_ERROR.name,
                additionalInfo = mapOf("error" to e.localizedMessage)
            )
        )
    }

    /**
     * 일반 전역 예외 처리
     */
    @ExceptionHandler(Exception::class)
    fun handleGeneralException(e: Exception): ApiResponse<Nothing> {
        return ApiResponse.error(
            code = GlobalErrorCode.INTERNAL_SERVER_ERROR.status,
            message = GlobalErrorCode.INTERNAL_SERVER_ERROR.message,
            errorDetails = ErrorDetails(
                errorCode = GlobalErrorCode.INTERNAL_SERVER_ERROR.name,
                additionalInfo = mapOf("exception" to e.javaClass.simpleName)
            )
        )
    }
}