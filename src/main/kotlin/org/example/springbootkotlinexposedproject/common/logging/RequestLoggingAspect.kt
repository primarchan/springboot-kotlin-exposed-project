package org.example.springbootkotlinexposedproject.common.logging

import java.time.LocalDateTime
import org.slf4j.LoggerFactory
import kotlin.system.measureNanoTime
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.ProceedingJoinPoint
import jakarta.servlet.http.HttpServletRequest
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.stereotype.Component
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Profile

@Aspect
@Profile("local")
@Component
class RequestLoggingAspect(
    private val httpServletRequest: HttpServletRequest
) {
    private val logger = LoggerFactory.getLogger(RequestLoggingAspect::class.java)
    private val objectMapper = ObjectMapper() // ObjectMapper를 클래스 필드로 선언하여 재사용

    @Around("within(@org.example.springbootkotlinexposedproject.common.annotation.EnableRequestLogging *)")
    fun logRequestAndResponse(joinPoint: ProceedingJoinPoint): Any? {
        val startTime = LocalDateTime.now()
        val methodSignature = joinPoint.signature as MethodSignature
        val controllerName = methodSignature.declaringTypeName
        val methodName = methodSignature.method.name

        val requestUri = httpServletRequest.requestURI
        val requestIp = httpServletRequest.remoteAddr
        val userId = httpServletRequest.getHeader("X-User-id") ?: "Unknown"

        // 메서드 실행 및 실행 시간 측정
        var result: Any? = null
        val durationNano = measureNanoTime {
            result = joinPoint.proceed()
        }

        // 파라미터를 JSON 또는 문자열로 직렬화
        val params = joinPoint.args.mapIndexed { index, arg ->
            "Param $index: ${serializeArgument(arg)}"
        }.joinToString(", ")

        // 로그 메시지 작성
        val logMessage = """    
            RequestLoggingAspect--------------------------------------------------------------------------------------------------
            API Controller Name       : $controllerName
            API Method Name           : $methodName
            API Params                : $params
            API Request Time          : $startTime
            API Request UserId        : $userId
            API Request URI           : $requestUri
            API Request IP            : $requestIp
            API Duration Nano Seconds : $durationNano
            -----------------------------------------------------------------------------------------------------
        """.trimIndent()

        logger.info("\n$logMessage")
        return result
    }

    /**
     * 객체를 JSON 문자열로 직렬화하거나 기본 문자열로 반환
     */
    private fun serializeArgument(arg: Any?): String {
        return try {
            objectMapper.writeValueAsString(arg)
        } catch (e: Exception) {
            arg.toString()
        }
    }
}