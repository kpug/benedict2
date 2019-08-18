package cc.before30.kotex.demokt.controller

import cc.before30.kotex.demokt.domain.MethodDescription
import cc.before30.kotex.demokt.domain.MethodDescriptionService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 *
 * RestApiController
 *
 * @author before30
 * @since 2019-08-17
 */
@RestController
class RestApiController(val methodDescriptionService: MethodDescriptionService) {

    @GetMapping("/api/all")
    fun findAll(): List<MethodDescription> {
        return methodDescriptionService.findAll()
    }
}