package cc.before30.kotex.demokt

import cc.before30.kotex.demokt.domain.MethodDescription
import cc.before30.kotex.demokt.domain.MethodDescriptionService
import cc.before30.kotex.demokt.util.FileExtractor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate

@SpringBootApplication
class DemoKtApplication : CommandLineRunner {
    @Autowired
    lateinit var elasticsearchTemplate: ElasticsearchTemplate

    @Autowired
    lateinit var methodDescriptionService: MethodDescriptionService

    override fun run(vararg args: String?) {
        val extract = FileExtractor.extractMethodName("/Users/before30/workspace/sandbox/demo-kt/src/main/resources/spring-framework-master.zip")
        extract.stream().forEach {
            methodDescriptionService.insert(it)
        }
    }
}

fun main(args: Array<String>) {
    runApplication<DemoKtApplication>(*args)
}
