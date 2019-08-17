package cc.before30.kotex.demokt.domain

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import org.springframework.stereotype.Repository

/**
 *
 * UserRepository
 *
 * @author before30
 * @since 2019-08-17
 */

@Repository("UserRepository")
interface UserRepository : ElasticsearchRepository<User, String> {
    fun findByName(name: String): User
}