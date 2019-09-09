package cc.kpug.benedict.core.domain

import org.elasticsearch.index.query.QueryBuilders.matchQuery
import org.elasticsearch.search.aggregations.AggregationBuilder
import org.elasticsearch.search.aggregations.AggregationBuilders
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms
import org.elasticsearch.search.aggregations.metrics.tophits.InternalTopHits
import org.elasticsearch.search.suggest.SuggestBuilder
import org.elasticsearch.search.suggest.completion.CompletionSuggestion
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder
import org.springframework.stereotype.Service
import java.lang.annotation.Native

/**
 *
 * MethodDescriptionService
 *
 * @author before30
 * @since 2019-08-17
 */
@Service
class MethodDescriptionService(val methodDescriptionRepository: MethodDescriptionRepository,
                               val elasticsearchTemplate: ElasticsearchTemplate) {

    val METHOD_SUGGESTION = "METHOD_SUGGESTION"

    fun insert(methodDescription: MethodDescription): MethodDescription {
        return methodDescriptionRepository.save(methodDescription)
    }

    fun findAll(): List<MethodDescription> {
       return methodDescriptionRepository
               .findAll(PageRequest.of(1, 100)).asSequence().toList()
    }

    fun suggest(query: String, indexName: String): List<String> {

        val suggestion = CompletionSuggestionBuilder("methodName.completion")
                .prefix(query)
                .size(10)
                .skipDuplicates(true)

        val suggestQuery = SuggestBuilder()
                .addSuggestion(METHOD_SUGGESTION, suggestion)
        val response = elasticsearchTemplate.client
                .prepareSearch(indexName)
                .suggest(suggestQuery)
                .setSize(0)
                .setTypes("_doc")
                .execute()
                .actionGet()

        val resutls = response.suggest
                .getSuggestion<CompletionSuggestion>(METHOD_SUGGESTION)
                .entries[0].options
                .map { it.text.toString() }

        return resutls
    }

    fun search(query: String, indexName: String): List<String> {
        val searchQuery = NativeSearchQueryBuilder()
                .withQuery(matchQuery("methodName.ngram", query))
                .build()

        val aggregation = AggregationBuilders
                .terms("dedup")
                .field("methodName")
                .subAggregation(
                        AggregationBuilders
                                .topHits("dedup_docs")
                                .size(1))
        val response = elasticsearchTemplate.client
                .prepareSearch(indexName)
                .setSize(0)
                .setTypes("_doc")
                .setQuery(searchQuery.query)
                .addAggregation(aggregation)
                .execute()
                .actionGet()

        val results = response.aggregations
                .flatMap { (it as StringTerms).buckets }
                .flatMap { it.aggregations }
                .flatMap { (it as InternalTopHits).hits.hits.toList() }
                .map { it.sourceAsMap.get("methodName").toString() }

        return results
    }
}