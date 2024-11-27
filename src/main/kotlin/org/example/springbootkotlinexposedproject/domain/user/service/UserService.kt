package org.example.springbootkotlinexposedproject.domain.user.service

import org.springframework.stereotype.Service
import org.example.springbootkotlinexposedproject.domain.user.dto.UserRequest
import org.example.springbootkotlinexposedproject.domain.user.dto.UserResponse
import org.example.springbootkotlinexposedproject.domain.user.mapper.toUserDomain
import org.example.springbootkotlinexposedproject.domain.post.mapper.toPostDomain
import org.example.springbootkotlinexposedproject.domain.user.mapper.toUserResponse
import org.example.springbootkotlinexposedproject.domain.post.mapper.toPostResponse
import org.example.springbootkotlinexposedproject.domain.user.repository.UserRepository
import org.example.springbootkotlinexposedproject.domain.user.dto.UserWithPostsResponse
import org.example.springbootkotlinexposedproject.domain.post.repository.PostRepository
import org.example.springbootkotlinexposedproject.domain.user.exception.UserErrorCode
import org.example.springbootkotlinexposedproject.domain.user.exception.UserServiceException
import org.example.springbootkotlinexposedproject.domain.user.mapper.toUserWithPostsResponse

@Service
class UserService(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository
) {

    /**
     * 모든 사용자 조회
     */
    fun getAllUsers(): List<UserResponse> =
        userRepository.findAll()
            .map { it.toUserDomain().toUserResponse() }

    /**
     * ID로 사용자 조회
     */
    fun getUserById(userId: Long): UserResponse =
        userRepository.findById(userId)
            ?.toUserDomain()
            ?.toUserResponse()
            ?: throw UserServiceException(UserErrorCode.USER_NOT_FOUND, mapOf("userId" to userId.toString()))

    /**
     * Email로 사용자 조회
     */
    fun getUserByUserEmail(userEmail: String): UserResponse =
        userRepository.findByUserEmail(userEmail)
            ?.toUserDomain()
            ?.toUserResponse()
            ?: throw UserServiceException(UserErrorCode.USER_ALREADY_EXISTS, mapOf("userEmail" to userEmail))

    /**
     * 사용자 생성
     */
    fun createUser(userRequest: UserRequest): UserResponse =
        userRepository.create(userRequest.userName, userRequest.userEmail)
            .toUserDomain()
            .toUserResponse()

    /**
     * 커서 기반 페이지네이션으로 사용자 조회
     * @param lastUserId 이전 페이지의 마지막 사용자 ID (nullable)
     * @param size 페이지 크기 (기본값: 10)
     */
    fun getUsersByCursor(lastUserId: Long? = null, size: Int): List<UserResponse> =
        userRepository.findAllByCursor(lastUserId, size)
            .map { it.toUserDomain().toUserResponse() }

    /**
     * 특정 사용자 정보와 작성한 포스트 조회
     * @param userId 사용자 ID
     * @return 사용자 정보와 작성한 포스트 리스트
     */
    fun getUserWithPosts(userId: Long): UserWithPostsResponse {
        val userResponse = userRepository.findById(userId)
            ?.toUserDomain()
            ?.toUserResponse()
            ?: throw UserServiceException(UserErrorCode.USER_NOT_FOUND, mapOf("userId" to userId.toString()))

        val posts = postRepository.findByUserId(userId)
            .map { it.toPostDomain().toPostResponse() }

        return userResponse.toUserWithPostsResponse(posts)
    }

    /**
     * Helper Method: UserServiceException 생성
     */
    private fun userException(errorCode: UserErrorCode, vararg additionInfo: Pair<String, Any>): UserServiceException {
        return UserServiceException(
            errorCode = errorCode,
            additionalInfo = additionInfo.toMap()
        )
    }
}