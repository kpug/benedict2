package cc.before30.kotex.demokt.config

import org.elasticsearch.client.Client
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.transport.TransportAddress
import org.elasticsearch.transport.client.PreBuiltTransportClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories
import java.net.InetAddress

/**
 *
 * DemoAppConfiguration
 *
 * @author before30
 * @since 2019-08-17
 */

@EnableElasticsearchRepositories
@Configuration
class DemoAppConfiguration {

    @Value("\${elasticsearch.host:127.0.0.1}")
    val host: String? = null
    @Value("\${elasticsearch.port:9300}")
    val port: Int = 0

    @Bean
    fun client(): Client {
        val settings = Settings.builder().put("cluster.name", "elasticsearch_before30").build()
        val client = PreBuiltTransportClient(settings)
        client.addTransportAddress(TransportAddress(InetAddress.getByName(host), port))
        return client
    }

    @Bean
    fun elasticsearchTemplate(client: Client): ElasticsearchTemplate {
        return ElasticsearchTemplate(client)
    }

}