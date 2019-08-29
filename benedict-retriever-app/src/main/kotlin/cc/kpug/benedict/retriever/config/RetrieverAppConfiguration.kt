package cc.kpug.benedict.retriever.config

import cc.kpug.benedict.core.domain.BenedictIndex
import org.elasticsearch.client.Client
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.transport.TransportAddress
import org.elasticsearch.transport.client.PreBuiltTransportClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories
import java.net.InetAddress

/**
 *
 * RetrieverAppConfiguration
 *
 * @author before30
 * @since 22/08/2019
 */

@Configuration
@EnableElasticsearchRepositories(basePackages = ["cc.kpug.benedict.core.domain"])
@ComponentScan(basePackages = ["cc.kpug.benedict.core.domain"])
class RetrieverAppConfiguration {
    @Value("\${elasticsearch.host:127.0.0.1}")
    val host: String? = null

    @Value("\${elasticsearch.port:9300}")
    val port: Int = 0

    @Value("\${elasticsearch.clustername:cluster}")
    val clusterName: String? = null

    @Bean
    fun client(): Client {
        val settings = Settings.builder().put("cluster.name", clusterName).build()
        val client = PreBuiltTransportClient(settings)
        client.addTransportAddress(TransportAddress(InetAddress.getByName(host), port))
        return client
    }

    @Bean
    fun elasticsearchTemplate(client: Client): ElasticsearchTemplate {
        return ElasticsearchTemplate(client)
    }

    @Bean
    fun benedictIndex(): BenedictIndex {
        return BenedictIndex
    }
}