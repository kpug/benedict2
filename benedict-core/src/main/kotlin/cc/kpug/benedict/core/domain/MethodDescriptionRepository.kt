package cc.kpug.benedict.core.domain

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import org.springframework.stereotype.Repository

/**
 *
 * MethodDescriptionRepository
 *
 * @author before30
 * @since 2019-08-17
 */
@Repository("MethodDescriptionRepository")
interface MethodDescriptionRepository : ElasticsearchRepository<MethodDescription, String> {
}