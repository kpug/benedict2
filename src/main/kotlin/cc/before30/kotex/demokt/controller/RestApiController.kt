package cc.before30.kotex.demokt.controller

import cc.before30.kotex.demokt.domain.User
import cc.before30.kotex.demokt.domain.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.lang.IllegalArgumentException

/**
 *
 * RestApiController
 *
 * @author before30
 * @since 2019-08-17
 */
@RestController
class RestApiController(val userService: UserService) {

    @GetMapping("/api/all")
    fun findAll(): List<User> {
        return userService.findAll()
    }

    @GetMapping("/api/name/{name}")
    fun findByName(@PathVariable name: String): User {
        return userService.findByName(name)
    }

    @GetMapping("/api/new/{name}")
    fun newUser(@PathVariable name: String): User {
        val user = User(null, name)
        return userService.save(user)
    }
//    @GetMapping("/api/new/{name}")
//    fun newUser(@PathVariable name: String, @RequestParam("test") testParam: String?): User = when(testParam) {
//        return userService.save(User(null, name))
//        else -> throw IllegalArgumentException("test")
//    }
}