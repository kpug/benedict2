package cc.kpug.benedict.retriever.controller

import cc.kpug.benedict.core.domain.MethodDescription
import cc.kpug.benedict.retriever.domain.MethodDescriptionSuggestionService
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
@RequestMapping("/method")
class RetrieverController(
        val methodDescriptionSuggestionService: MethodDescriptionSuggestionService) {

    @GetMapping("/search/{query}")
    fun searchMethod(@PathVariable query: String) =
            methodDescriptionSuggestionService.search(query)

    @GetMapping("/suggest/{query}")
    fun suggestMethod(@PathVariable query: String) =
            methodDescriptionSuggestionService.suggest(query)

    @GetMapping("/mix/{query}")
    fun suggestAndSearchMethod(@PathVariable query: String) =
            methodDescriptionSuggestionService.suggestAndSearch(query)
}