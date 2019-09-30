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
import java.io.InputStreamReader
import java.io.LineNumberReader
import java.util.stream.Collectors


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
        logger.info("hello world")
        // create index

        logger.info("indexname=${benedictIndex.name}")
        elasticsearchTemplate.deleteIndex(benedictIndex.name)
        elasticsearchTemplate.createIndex(benedictIndex.name, loadFromFile("/settings/method-analyzer.json"))
        elasticsearchTemplate.putMapping(benedictIndex.name, "_doc", loadFromFile("/mappings/method-mapping.json"))
        elasticsearchTemplate.refresh(benedictIndex.name)

        // insert data
        val filePath = this::class.java.getResource("/spring-framework-master.zip").path
        val extract = FileExtractor.extractMethodName(filePath)
        var buffer = ArrayList<MethodDescription>()
        for (method in extract) {
            buffer.add(method)
            if (buffer.size > 100) {
                methodDescriptionService.bulkInsert(buffer)
                buffer = ArrayList<MethodDescription>()
            }
        }

        if (buffer.size > 0) {
            methodDescriptionService.bulkInsert(buffer)
        }
        benedictAliasService.apply(benedictIndex.name)
        logger.info("insertion done.")
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