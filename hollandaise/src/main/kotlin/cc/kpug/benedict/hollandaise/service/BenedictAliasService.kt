package cc.kpug.benedict.hollandaise.service

import cc.kpug.benedict.core.domain.BenedictEsConstants
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest.AliasActions
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest




@Service
class BenedictAliasService(
        @Qualifier("elasticsearchRestHighLevelClient")
        private val esClient: RestHighLevelClient
) {
    /*
    * https://www.elastic.co/guide/en/elasticsearch/client/java-rest/master/java-rest-high-update-aliases.html
    * */

    fun apply(index: String) {

        val addIndexAction = AliasActions(AliasActions.Type.ADD)
                .index(index)
                .alias(BenedictEsConstants.ALIAS)

        val removeIndexActions =  getIndices(BenedictEsConstants.ALIAS).map {
            AliasActions(AliasActions.Type.REMOVE_INDEX).index(it)
        }
        val request = removeIndexActions.fold(
                IndicesAliasesRequest().addAliasAction(addIndexAction),
                { request, action -> request.addAliasAction(action)}
        )

        esClient.indices().updateAliases(request, RequestOptions.DEFAULT)
    }

    private fun getIndices(alias: String): List<String> {

        val request = GetAliasesRequest(alias)
        val aliasInfo = esClient.indices().getAlias(request, RequestOptions.DEFAULT)
        return aliasInfo.aliases.keys.toList()
    }
}
