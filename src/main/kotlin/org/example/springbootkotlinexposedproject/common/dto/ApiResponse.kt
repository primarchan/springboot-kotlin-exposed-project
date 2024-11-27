package org.example.springbootkotlinexposedproject.common.dto

data class ApiResponse<T>(
    val status: Status,                     // 응답 상태 정보
    val message: String? = null,            // 클라이언트에 전달할 메시지
    val data: T? = null,                    // 실제 데이터
    val meta: Meta? = null,                 // 추가 정보 (페이징, 요청 ID 등)
    val errorDetails: ErrorDetails? = null  // 에러 세부 정보(에러 코드, 추가 정보)
) {
    companion object {
        fun <T> success(data: T? = null, message: String? = null): ApiResponse<T> =
            ApiResponse(status = Status(code = 200, success = true), data = data, message = message)

        fun <T> created(data: T? = null, message: String? = null): ApiResponse<T> =
            ApiResponse(status = Status(code = 201, success = true), data = data, message = message)

        fun <T> error(
            code: Int,
            message: String,
            errorDetails: ErrorDetails? = null
        ): ApiResponse<T> =
            ApiResponse(status = Status(code = code, success = false), message = message, errorDetails = errorDetails)
    }
}

data class Status(
    val code: Int,                      // HTTP 상태 코드
    val success: Boolean                // 성공 여부
)

data class Meta(
    val requestId: String? = null,      // 요청 고유 ID (추적 가능)
    val pagination: Pagination? = null  // 페이징 정보
)

data class Pagination(
    val totalCount: Long,               // 전체 데이터 개수
    val currentPage: Int,               // 현재 페이지 번호
    val size: Int                       // 페이지 크기
)

data class ErrorDetails(
    val errorCode: String,                          // 에러 코드
    val additionalInfo: Map<String, Any>? = null    // 추가 에러 정보
)