package cc.kpug.benedict.insertion.config

import org.apache.http.HttpHost
import org.elasticsearch.client.RestClient
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ElasticsearchConfig {

    @Value("\${elasticsearch.host}")
    lateinit var host: String

    @Value("\${elasticsearch.port}")
    lateinit var port: Integer

    @Bean(destroyMethod = "close")
    fun elasticsearchRestHighLevelClient(): RestHighLevelClient {
        return RestHighLevelClient(RestClient.builder(HttpHost(host, port.toInt())))
    }
}