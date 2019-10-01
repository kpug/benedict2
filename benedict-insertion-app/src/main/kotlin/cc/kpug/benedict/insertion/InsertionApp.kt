package cc.kpug.benedict.insertion

import cc.kpug.benedict.core.domain.BenedictIndex
import cc.kpug.benedict.core.domain.MethodDescription
import cc.kpug.benedict.insertion.service.BenedictAliasService
import cc.kpug.benedict.insertion.util.HttpExtractor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate
import org.springframework.data.elasticsearch.core.query.IndexQuery
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder
import java.io.InputStreamReader
import java.io.LineNumberReader
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
    lateinit var benedictIndex: BenedictIndex

    @Autowired
    lateinit var benedictAliasService: BenedictAliasService

    override fun run(vararg args: String?) {
        logger.info("hello world")
        // create index

        logger.info("indexname=${benedictIndex.name}")
        elasticsearchTemplate.deleteIndex(benedictIndex.name)
        elasticsearchTemplate.createIndex(benedictIndex.name, loadFromFile("/settings/method-analyzer.json"))
        elasticsearchTemplate.putMapping(benedictIndex.name, "_doc", loadFromFile("/mappings/method-mapping.json"))
        elasticsearchTemplate.refresh(benedictIndex.name)

        // insert data
        val filePath = this::class.java.getResource("/spring-framework-master.zip").path
        // TODO : Stream 형태로 변환
        val repositories = listOf(
            "https://github.com/spring-projects/spring-framework/archive/v5.2.0.RELEASE.zip",
            "https://github.com/spring-projects/spring-boot/archive/v2.1.8.RELEASE.zip",
            "https://github.com/resilience4j/resilience4j/archive/v1.1.0.zip",
            "https://github.com/apache/tomcat/archive/9.0.26.zip"
//            "https://github.com/openjdk/jdk/archive/jdk-14+16.zip"
        )

        for (repo in repositories) {
            val extract = HttpExtractor.extractMethodName(repo)
            var buffer = ArrayList<MethodDescription>()
            var buffer2 = ArrayList<IndexQuery>()
            for (method in extract) {
//                buffer.add(method)
                buffer2.add(IndexQueryBuilder().withIndexName(benedictIndex.name).withObject(method).build())
                if (buffer.size > 100) {

                    elasticsearchTemplate.bulkIndex(buffer2)
                    buffer2 = ArrayList<IndexQuery>()
//                    methodDescriptionService.bulkInsert(buffer)
//                    buffer = ArrayList<MethodDescription>()
                }
            }

            if (buffer2.size > 0) {
//                methodDescriptionService.bulkInsert(buffer)
                elasticsearchTemplate.bulkIndex(buffer2)
            }
        }

        benedictAliasService.apply(benedictIndex.name)
        logger.info("insertion done.")
        // TODO: App 종료 왜 안되는지 확인
    }

    @Throws(IllegalStateException::class)
    fun loadFromFile(fileName: String): String {
        val buffer = StringBuilder(2048)
        try {
            val `is` = this::class.java.getResourceAsStream(fileName)
            val reader = LineNumberReader(InputStreamReader(`is`))
            while (reader.ready()) {
                buffer.append(reader.readLine())
                buffer.append(' ')
            }
        } catch (e: Exception) {
            throw IllegalStateException("couldn't load file $fileName", e)
        }

        return buffer.toString()
    }
}


fun main(args: Array<String>) {
    runApplication<InsertionApp>(*args)
}