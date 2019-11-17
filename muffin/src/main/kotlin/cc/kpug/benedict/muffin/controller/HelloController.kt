package cc.kpug.benedict.muffin.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 *
 * HelloController
 *
 * @author before30
 * @since 23/08/2019
 */
@RestController
class HelloController {

    @GetMapping("/hello")
    fun hello() = "world"
}