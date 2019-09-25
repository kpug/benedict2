package cc.kpug.benedict.retriever

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.CrossOrigin

/**
 *
 * RetrieverApp
 *
 * @author before30
 * @since 22/08/2019
 */
@SpringBootApplication
@CrossOrigin
class RetrieverApp {
}

fun main(args: Array<String>) {
    runApplication<RetrieverApp>(*args)
}