package cc.kpug.benedict.core.domain

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.*

/**
 *
 * MethodDescription
 *
 * @author before30
 * @since 2019-08-17
 */
@Document(indexName = "#{benedictIndex.name}", type = "_doc")
class MethodDescription(
        @Id
        var id: String? = null,
        val methodName: String,
        val methodSignature: String
) {
    constructor(): this(null, "", "")

    override fun toString(): String {
        return "methodName=$methodName, methodSignature=$methodSignature"
    }
}