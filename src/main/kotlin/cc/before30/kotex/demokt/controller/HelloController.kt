package cc.before30.kotex.demokt.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 *
 * HelloController
 *
 * @author before30
 * @since 2019-08-17
 */

@RestController
class HelloController {

    @GetMapping("/hello")
    fun hello(): String {
        return "world"
    }
}