package org.example.springbootkotlinexposedproject.domain.user.service

import org.springframework.stereotype.Service
import org.example.springbootkotlinexposedproject.domain.user.dto.*
import org.example.springbootkotlinexposedproject.domain.user.mapper.toUserDomain
import org.example.springbootkotlinexposedproject.domain.post.mapper.toPostDomain
import org.example.springbootkotlinexposedproject.domain.user.exception.UserErrorCode
import org.example.springbootkotlinexposedproject.domain.user.repository.UserRepository
import org.example.springbootkotlinexposedproject.domain.post.repository.PostRepository
import org.example.springbootkotlinexposedproject.domain.user.mapper.toUserWithPostsDomain
import org.example.springbootkotlinexposedproject.domain.user.exception.UserServiceException

@Service
class UserService(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository
) {

    /**
     * 모든 사용자 조회
     */
    fun getAllUsers(): List<UserDomain> =
        userRepository.findAll()
            .map { it.toUserDomain() }

    /**
     * ID로 사용자 조회
     */
    fun getUserById(userId: Long): UserDomain =
        userRepository.findById(userId)
            ?.toUserDomain()
            ?: throw buildUserServiceException(UserErrorCode.USER_NOT_FOUND, "userId" to userId.toString())

    /**
     * Email로 사용자 조회
     */
    fun getUserByUserEmail(userEmail: String): UserDomain =
        userRepository.findByUserEmail(userEmail)
            ?.toUserDomain()
            ?: throw buildUserServiceException(UserErrorCode.USER_ALREADY_EXISTS, "userEmail" to userEmail)

    /**
     * 사용자 생성
     */
    fun createUser(userRequest: UserRequest): UserDomain =
        userRepository.create(userRequest.userName, userRequest.userEmail)
            .toUserDomain()

    /**
     * 커서 기반 페이지네이션으로 사용자 조회
     * @param lastUserId 이전 페이지의 마지막 사용자 ID (nullable)
     * @param size 페이지 크기 (기본값: 10)
     */
    fun getUsersByCursor(lastUserId: Long? = null, size: Int): List<UserDomain> =
        userRepository.findAllByCursor(lastUserId, size)
            .map { it.toUserDomain() }

    /**
     * 특정 사용자 정보와 작성한 포스트 조회
     * @param userId 사용자 ID
     * @return 사용자 정보와 작성한 포스트 리스트
     */
    fun getUserWithPosts(userId: Long): UserWithPostsDomain {
        val user = userRepository.findById(userId)
            ?.toUserDomain()
            ?: throw buildUserServiceException(UserErrorCode.USER_NOT_FOUND, "userId" to userId.toString())

        val userPosts = postRepository.findByUserId(userId)
            .map { it.toPostDomain() }

        return user.toUserWithPostsDomain(userPosts)
    }

    fun getUserWithPostsUsingJoin(userId: Long): UserWithPostsDomain {
        val rows = userRepository.findUserWithPostsByUserIdUsingJoin(userId)
            .takeIf { it.isNotEmpty() }
            ?:throw buildUserServiceException(UserErrorCode.USER_NOT_FOUND, "userId" to userId.toString())

        val user = rows.first().toUserDomain()
        val userPosts = rows.map { it.toPostDomain() }
        return user.toUserWithPostsDomain(userPosts)
    }

    /**
     * Helper Method: UserServiceException 생성
     */
    private fun buildUserServiceException(errorCode: UserErrorCode, vararg additionInfo: Pair<String, Any>): UserServiceException =
        UserServiceException(
            errorCode = errorCode,
            additionalInfo = additionInfo.toMap()
        )
}