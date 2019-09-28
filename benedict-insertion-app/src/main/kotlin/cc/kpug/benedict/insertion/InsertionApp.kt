package cc.kpug.benedict.insertion

import cc.kpug.benedict.core.domain.MethodDescription
import cc.kpug.benedict.core.domain.MethodDescriptionService
import cc.kpug.benedict.core.domain.BenedictIndex
import cc.kpug.benedict.insertion.service.BenedictAliasService
import cc.kpug.benedict.insertion.util.FileExtractor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate
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

    @Autowired
    lateinit var benedictAliasService: BenedictAliasService

    override fun run(vararg args: String?) {
//        logger.info("hello world")
//        // create index
//        val indexName = "benedict_${ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))}"
//        benedictIndex.name = indexName
//
//        logger.info("indexname=${indexName}")
//
//        elasticsearchTemplate.createIndex(MethodDescription::class.java)
//        elasticsearchTemplate.putMapping(MethodDescription::class.java)
//        elasticsearchTemplate.refresh(MethodDescription::class.java)
//        // insert data
//        val filePath = this::class.java.getResource("/spring-framework-master.zip").path
//        val extract = FileExtractor.extractMethodName(filePath)
//        extract.stream().forEach {
//            methodDescriptionService.insert(it)
//        }
//
//        benedictAliasService.apply(indexName)
//
//        logger.info("insertion done.")
        benedictAliasService.apply("benedict_20190916211925")
    }
}

fun main(args: Array<String>) {
    runApplication<InsertionApp>(*args)
}