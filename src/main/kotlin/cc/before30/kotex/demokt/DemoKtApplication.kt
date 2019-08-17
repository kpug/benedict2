package cc.before30.kotex.demokt

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate

@SpringBootApplication
class DemoKtApplication : CommandLineRunner {
    @Autowired
    lateinit var elasticsearchTemplate: ElasticsearchTemplate

    override fun run(vararg args: String?) {
        
    }
}

fun main(args: Array<String>) {
    runApplication<DemoKtApplication>(*args)
}
