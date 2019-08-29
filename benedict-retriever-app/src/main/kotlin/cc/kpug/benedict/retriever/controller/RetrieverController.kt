package cc.kpug.benedict.retriever.controller

import cc.kpug.benedict.core.domain.BenedictIndex
import cc.kpug.benedict.core.domain.MethodDescription
import cc.kpug.benedict.core.domain.MethodDescriptionService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 *
 * RetrieverController
 *
 * @author before30
 * @since 23/08/2019
 */
@RestController
@RequestMapping("/search/method")
class RetrieverController(val methodDescriptionService: MethodDescriptionService,
                          val benedictIndex: BenedictIndex) {
    @GetMapping("/all")
    fun findAll(): List<MethodDescription> {
        return methodDescriptionService.findAll()
    }

    @GetMapping("/{query}")
    fun searchMethod(@PathVariable query: String) =
            methodDescriptionService.search(query, "benedict" + benedictIndex.name)
}