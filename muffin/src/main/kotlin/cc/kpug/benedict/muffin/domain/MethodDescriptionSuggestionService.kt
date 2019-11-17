package cc.kpug.benedict.muffin.domain

import cc.kpug.benedict.core.domain.BenedictEsConstants
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.aggregations.AggregationBuilders
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms
import org.elasticsearch.search.aggregations.metrics.tophits.InternalTopHits
import org.elasticsearch.search.suggest.SuggestBuilder
import org.elasticsearch.search.suggest.completion.CompletionSuggestion
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder
import org.springframework.stereotype.Service

/**
 *
 * MethodDescriptionService
 *
 * @author before30
 * @since 2019-08-17
 */
@Service
class MethodDescriptionSuggestionService(val elasticsearchTemplate: ElasticsearchTemplate) {

    fun suggestAndSearch(query: String): List<String> {
        val resp = ArrayList<String>()

        resp.addAll(suggest(query))
        if (resp.size < 10) {
            resp.addAll(search(query))

        }

        return resp.subList(0, Math.min(10, resp.size))
    }

    // TODO: 중복 제거
    fun suggest(query: String): List<String> {

        val suggestion = CompletionSuggestionBuilder("methodName.completion")
                .prefix(query)
                .size(10)
                .skipDuplicates(true)

        val suggestQuery = SuggestBuilder()
                .addSuggestion(BenedictEsConstants.METHOD_SUGGESTION, suggestion)
        val response = elasticsearchTemplate.client
                .prepareSearch(BenedictEsConstants.ALIAS)
                .suggest(suggestQuery)
                .setSize(0)
                .setTypes(BenedictEsConstants.TYPE_NAME)
                .execute()
                .actionGet()

        val resutls = response.suggest
                .getSuggestion<CompletionSuggestion>(BenedictEsConstants.METHOD_SUGGESTION)
                .entries[0].options
                .map { it.text.toString() }

        return resutls
    }

    // TODO: 중복 제거
    fun search(query: String): List<String> {
        val searchQuery = NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("methodName.ngram", query))
                .build()

        val aggregation = AggregationBuilders
                .terms("dedup")
                .field("methodName")
                .subAggregation(
                        AggregationBuilders
                                .topHits("dedup_docs")
                                .size(1))
        val response = elasticsearchTemplate.client
                .prepareSearch(BenedictEsConstants.ALIAS)
                .setSize(0)
                .setTypes(BenedictEsConstants.TYPE_NAME)
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