package cc.before30.kotex.demokt.util

import cc.before30.kotex.demokt.domain.MethodDescription
import java.util.regex.Pattern
import java.util.stream.Collectors.toList
import java.util.zip.ZipFile


/**
 *
 * FileExtractor
 *
 * @author before30
 * @since 2019-08-18
 */
object FileExtractor {
    val pattern = Pattern.compile("(public|protected|private|static|\\s) +([\\w\\<\\>\\[\\]]+)\\s+(\\w+) *(\\([^\\)]*\\)) *(\\{?|[^;])")

    fun extractClassName(path: String): List<String> {
        val zipFile = ZipFile(path)
        return zipFile.stream()
                .map { v -> v.name }
                .filter { v -> !v.contains("/src/test/java")}
                .filter { v -> v.endsWith(".java")}
                .filter { v -> !v.endsWith("package-info.java")}
                .filter { v -> !v.endsWith("Test.java")}
                .peek { v -> println(v) }
                .collect(toList())
    }

    fun extractMethodName(path: String): List<MethodDescription> {
        val zipFile = ZipFile(path)
        return zipFile.stream()
                .filter { v -> !v.isDirectory }
                .filter { v -> !v.name.contains("/src/test/java")}
                .filter { v -> v.name.endsWith(".java")}
                .filter { v -> !v.name.endsWith("package-info.java")}
                .filter { v -> !v.name.endsWith("Test.java")}
                .map { v -> zipFile.getInputStream(v).buffered().reader().use {
                    reader -> reader.readLines()
                } }
                .flatMap { it -> it.stream().filter { v -> pattern.matcher(v).find() } }
                .map { it ->
                        val matcher = pattern.matcher(it)
                        matcher.find()
                        MethodDescription("", matcher.group(3), matcher.group(2) + " " + matcher.group(3) + matcher.group(4))
                }
                .peek { v -> println(v.name + " : " + v.fullDescription) }
                .collect(toList())
    }
}

fun main(args: Array<String>) {

//    val path = {}.javaClass::class.java.getResource("spring-framework-master.zip").path
    val extract = FileExtractor.extractMethodName("/Users/before30/workspace/sandbox/demo-kt/src/main/resources/spring-framework-master.zip")
    println(extract.size)
}