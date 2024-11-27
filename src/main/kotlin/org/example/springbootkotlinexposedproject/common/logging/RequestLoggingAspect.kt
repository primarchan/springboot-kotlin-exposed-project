package org.example.springbootkotlinexposedproject.common.logging

import java.time.LocalDateTime
import org.slf4j.LoggerFactory
import kotlin.system.measureNanoTime
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.ProceedingJoinPoint
import jakarta.servlet.http.HttpServletRequest
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Aspect
@Profile("local")
@Component
class RequestLoggingAspect(
    private val httpServletRequest: HttpServletRequest
) {
    private val logger = LoggerFactory.getLogger(RequestLoggingAspect::class.java)

    @Around("within(@org.example.springbootkotlinexposedproject.common.annotation.EnableRequestLogging *)")
    fun logRequestAndResponse(joinPoint: ProceedingJoinPoint): Any? {
        val startTime = LocalDateTime.now()
        val methodSignature = joinPoint.signature as MethodSignature
        val controllerName = methodSignature.declaringTypeName
        val methodName = methodSignature.method.name

        val requestUri = httpServletRequest.requestURI
        val requestIp = httpServletRequest.remoteAddr
        val userId = httpServletRequest.getParameter("X-User-id") ?: "Unknown"

        var result: Any? = null
        val durationNano = measureNanoTime {
            result = joinPoint.proceed()
        }

        val logMessage =
            """
                
            RequestLoggingAspect--------------------------------------------------------------------------------------------------
            API Controller Name       : $controllerName
            API Method Name           : $methodName
            API Params                : ${joinPoint.args.joinToString { it.toString() }}
            API Request Time          : $startTime
            API Request UserId        : $userId
            API Request URI           : $requestUri
            API Request IP            : $requestIp
            API Duration Nano Seconds : $durationNano
            -----------------------------------------------------------------------------------------------------
            
            """.trimIndent()

        logger.info(logMessage)
        return result
    }
}