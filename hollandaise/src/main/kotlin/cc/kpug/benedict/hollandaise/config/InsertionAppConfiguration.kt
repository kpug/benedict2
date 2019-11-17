package cc.kpug.benedict.hollandaise.config

import cc.kpug.benedict.core.domain.BenedictIndex
import org.elasticsearch.client.Client
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.transport.TransportAddress
import org.elasticsearch.transport.client.PreBuiltTransportClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate
import java.net.InetAddress
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 *
 * InsertionAppConfiguration
 *
 * @author before30
 * @since 23/08/2019
 */

@Configuration
class InsertionAppConfiguration {
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
        val indexName = "benedict_${LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))}"
        return BenedictIndex(indexName)
    }
}