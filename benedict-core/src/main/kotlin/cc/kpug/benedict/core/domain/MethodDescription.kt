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
@Document(indexName = "benedict#{benedictIndex.name}", type = "_doc", shards = 1, replicas = 0)
@Setting(settingPath = "/settings/method-analyzer.json")
@Mapping(mappingPath = "/mappings/method-mapping.json")
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