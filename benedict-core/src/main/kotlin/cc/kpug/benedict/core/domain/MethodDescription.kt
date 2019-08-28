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
class MethodDescription(
        @Id
        var id: String? = null,
        @MultiField(mainField = Field(type = FieldType.Text, analyzer = "method_name_analyzer", fielddata = true),
                otherFields = [InnerField(suffix = "ngram", type = FieldType.Text, analyzer = "method_name_ngram_analyzer")])
        val methodName: String,
        @Field(type = FieldType.Keyword, index = false)
        val methodSignature: String
) {
    constructor(): this(null, "", "")

    override fun toString(): String {
        return "methodName=$methodName, methodSignature=$methodSignature"
    }
}