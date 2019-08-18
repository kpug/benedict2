package cc.before30.kotex.demokt.domain

import org.springframework.stereotype.Service

/**
 *
 * MethodDescriptionService
 *
 * @author before30
 * @since 2019-08-17
 */
@Service
class MethodDescriptionService(val methodDescriptionRepository: MethodDescriptionRepository) {

    fun insert(methodDescription: MethodDescription): MethodDescription {
        return methodDescriptionRepository.save(methodDescription)
    }

    fun findAll(): List<MethodDescription> {
        return methodDescriptionRepository.findAll().asSequence().toList()
    }
}