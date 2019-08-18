package cc.before30.kotex.demokt.domain

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document

/**
 *
 * MethodDescription
 *
 * @author before30
 * @since 2019-08-17
 */
@Document(indexName = "benedict", type = "method")
class MethodDescription(
        @Id
        var id: String? = null,
        val projectName: String,
        val filePath: String,
        val name: String
) {

}