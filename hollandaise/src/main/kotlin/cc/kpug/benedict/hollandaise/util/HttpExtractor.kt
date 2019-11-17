package cc.kpug.benedict.hollandaise.util

import cc.kpug.benedict.core.domain.MethodDescription
import org.apache.commons.io.FileUtils
import java.io.File
import java.net.URL
import java.util.*

/**
 *
 * HttpExtractor
 *
 * @author before30
 * @since 01/10/2019
 */
object HttpExtractor {
    // download to tmp dir
    fun extractMethodName(url: String): List<MethodDescription> {
        val tmpDirPath = FileUtils.getTempDirectory().absolutePath
        val tmpFile = File(tmpDirPath + "/" + UUID.randomUUID() + ".zip")
        tmpFile.deleteOnExit()

        FileUtils.copyURLToFile(URL(url), tmpFile, 5000, 5000)
        return FileExtractor.extractMethodName(tmpFile.path)
    }
}