package cc.before30.kotex.demokt.domain

import org.springframework.stereotype.Service

/**
 *
 * UserService
 *
 * @author before30
 * @since 2019-08-17
 */
@Service
class UserService(val userRepository: UserRepository) {
    fun save(user: User): User {
        return userRepository.save(user)
    }

    fun findAll(): List<User> {
        return userRepository.findAll().asSequence().toList()
    }

    fun findByName(name: String): User {
        return userRepository.findByName(name)
    }

}