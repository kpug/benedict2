package cc.kpug.benedict.insertion

import cc.kpug.benedict.core.domain.MethodDescription
import cc.kpug.benedict.core.domain.MethodDescriptionService
import cc.kpug.benedict.core.domain.BenedictIndex
import cc.kpug.benedict.insertion.util.FileExtractor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate
import org.springframework.data.elasticsearch.core.query.AliasBuilder
import org.springframework.data.elasticsearch.core.query.AliasQuery
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
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

    @Autowired
    lateinit var benedictIndex: BenedictIndex

    override fun run(vararg args: String?) {
        logger.info("hello world")
        // create index
        benedictIndex.name = "_${ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))}"
        logger.info("indexname=benedict${benedictIndex.name}")
        elasticsearchTemplate.createIndex(MethodDescription::class.java)

        // insert data
        val extract = FileExtractor.extractMethodName("/Users/before30/workspace/sandbox/demo-kt/src/main/resources/spring-framework-master.zip")
        extract.stream().forEach {
            methodDescriptionService.insert(it)
        }

        val aliasQuery = AliasQuery()
        aliasQuery.aliasName = "benedict_alias"
        aliasQuery.indexName = "benedict${benedictIndex.name}"
        elasticsearchTemplate.addAlias(aliasQuery)

    }
}

fun main(args: Array<String>) {
    runApplication<InsertionApp>(*args)
}