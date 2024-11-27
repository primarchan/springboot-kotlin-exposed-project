package org.example.springbootkotlinexposedproject.domain.user.controller

import jakarta.validation.constraints.Min
import org.example.springbootkotlinexposedproject.common.annotation.EnableRequestLogging
import org.springframework.web.bind.annotation.*
import org.example.springbootkotlinexposedproject.common.dto.ApiResponse
import org.example.springbootkotlinexposedproject.domain.user.dto.UserRequest
import org.example.springbootkotlinexposedproject.domain.user.dto.UserResponse
import org.example.springbootkotlinexposedproject.domain.user.dto.UserWithPostsResponse
import org.example.springbootkotlinexposedproject.domain.user.service.UserService

@RestController
@RequestMapping("/users")
@EnableRequestLogging
class UserController(private val userService: UserService) {

    /**
     * 모든 사용자 조회
     */
    @GetMapping
    fun getAllUsers(): ApiResponse<List<UserResponse>> =
        ApiResponse.success(data = userService.getAllUsers())

    /**
     * ID로 사용자 조회
     */
    @GetMapping("/{userId}")
    fun getUserById(@PathVariable userId: Long): ApiResponse<UserResponse> =
        ApiResponse.success(data = userService.getUserById(userId))

    /**
     * 이메일로 사용자 조회
     */
    @GetMapping("/search")
    fun getUserByEmail(@RequestParam("user_email") userEmail: String): ApiResponse<UserResponse> =
        ApiResponse.success(data = userService.getUserByUserEmail(userEmail))

    /**
     * 사용자 생성
     */
    @PostMapping
    fun createUser(@RequestBody userRequest: UserRequest): ApiResponse<UserResponse> =
        ApiResponse.created(data = userService.createUser(userRequest))

    /**
     * 커서 기반 페이지네이션 사용자 조회
     */
    @GetMapping("/cursor")
    fun getUsersByCursor(
        @RequestParam(required = false) lastUserId: Long?,
        @RequestParam(defaultValue = "10") @Min(1) size: Int
    ): ApiResponse<List<UserResponse>> =
        ApiResponse.success(data = userService.getUsersByCursor(lastUserId, size))

    /**
     * 특정 사용자정보와 작성한 포스트 조회
     */
    @GetMapping("/{userId}/posts")
    fun getUserWithPosts(@PathVariable userId: Long): ApiResponse<UserWithPostsResponse> =
        ApiResponse.success(data = userService.getUserWithPosts(userId))
}