package cc.kpug.benedict.insertion

import cc.kpug.benedict.core.domain.MethodDescriptionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate
import java.util.logging.Logger.getLogger

/**
 *
 * InsertionApp
 *
 * @author before30
 * @since 22/08/2019
 */
@SpringBootApplication
class InsertionApp: CommandLineRunner {
    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        private val logger = getLogger(javaClass.enclosingClass.toString())
    }

    @Autowired
    lateinit var elasticsearchTemplate: ElasticsearchTemplate

    @Autowired
    lateinit var methodDescriptionService: MethodDescriptionService

    override fun run(vararg args: String?) {
        logger.info("hello world")
//        val extract = FileExtractor.extractMethodName("/Users/before30/workspace/sandbox/demo-kt/src/main/resources/spring-framework-master.zip")
//        extract.stream().forEach {
//            methodDescriptionService.insert(it)
//        }
    }
}

fun main(args: Array<String>) {
    runApplication<InsertionApp>(*args)
}