package cc.kpug.benedict.insertion

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 *
 * InsertionApp
 *
 * @author before30
 * @since 22/08/2019
 */
@SpringBootApplication
class InsertionApp: CommandLineRunner {
    override fun run(vararg args: String?) {
        println("hello world!")
    }
}

fun main(args: Array<String>) {
    runApplication<InsertionApp>(*args)
}