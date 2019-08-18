package cc.before30.kotex.demokt.util

import java.util.stream.Collectors.toList
import java.io.File.separator
import java.util.zip.ZipEntry
import java.io.IOException
import java.io.BufferedInputStream
import java.util.zip.ZipInputStream
import java.io.InputStream
import java.util.stream.Stream
import java.util.Enumeration
import java.util.zip.ZipFile




/**
 *
 * FileExtractor
 *
 * @author before30
 * @since 2019-08-18
 */
object FileExtractor {
    fun extract(path: String): List<String>? {
        val zipFile = ZipFile(path)
        val entries = zipFile.entries()
        while (entries.hasMoreElements()) {
            val entry = entries.nextElement()
            println(entry.name)
//            val stream = zipFile.getInputStream(entry)

        }
//
        return null
    }
}

fun main(args: Array<String>) {

//    val path = {}.javaClass::class.java.getResource("spring-framework-master.zip").path
    FileExtractor.extract("/Users/before30/workspace/sandbox/demo-kt/src/main/resources/spring-framework-master.zip")
}