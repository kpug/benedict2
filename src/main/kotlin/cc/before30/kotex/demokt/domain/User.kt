package cc.before30.kotex.demokt.domain

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import java.util.*
import kotlin.collections.HashMap


/**
 *
 * User
 *
 * @author before30
 * @since 2019-08-17
 */

@Document(indexName = "users", type = "employee" )
class User(
        @Id
        var userId: String? = null,
        val name: String,
        val creationDate: Date = Date(),
        val userSettings: HashMap<String, String> = HashMap()
) {
    constructor() : this(null, "")

}