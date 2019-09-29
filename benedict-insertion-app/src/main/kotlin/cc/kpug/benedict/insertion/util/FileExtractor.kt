package cc.kpug.benedict.insertion.util

import cc.kpug.benedict.core.domain.MethodDescription
import java.util.regex.Pattern
import java.util.stream.Collectors.toList
import java.util.stream.Stream
import java.util.zip.ZipFile

/**
 *
 * FileExtractor
 *
 * @author before30
 * @since 23/08/2019
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
                .filter { !it.isDirectory }
                .filter { !it.name.contains("/src/test/java")}
                .filter { it.name.endsWith(".java")}
                .filter { !it.name.endsWith("package-info.java")}
                .filter { !it.name.endsWith("Test.java")}
                .map { zipFile.getInputStream(it).buffered().reader().use {
                    reader -> reader.readLines()
                } }
                .flatMap { it.stream().filter { v -> pattern.matcher(v).find() } }
                .map {
                    val matcher = pattern.matcher(it)
                    matcher.find()
                    MethodDescription(null, matcher.group(3), matcher.group(2) + " " + matcher.group(3) + matcher.group(4))
                }
                .collect(toList())
    }

    fun extractMethodNameStream(path: String): Stream<MethodDescription> {
        val zipFile = ZipFile(path)
        return zipFile.stream()
                .filter { !it.isDirectory }
                .filter { !it.name.contains("/src/test/java")}
                .filter { it.name.endsWith(".java")}
                .filter { !it.name.endsWith("package-info.java")}
                .filter { !it.name.endsWith("Test.java")}
                .map { zipFile.getInputStream(it).buffered().reader().use {
                    reader -> reader.readLines()
                } }
                .flatMap { it.stream().filter { v -> pattern.matcher(v).find() } }
                .map {
                    val matcher = pattern.matcher(it)
                    matcher.find()
                    MethodDescription(null, matcher.group(3), matcher.group(2) + " " + matcher.group(3) + matcher.group(4))
                }
    }
}