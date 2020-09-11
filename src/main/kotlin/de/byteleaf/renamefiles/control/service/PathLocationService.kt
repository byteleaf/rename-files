package de.byteleaf.renamefiles.control.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.File
import java.lang.IllegalStateException

@Service
class PathLocationService {

    @Value("\${root-folder.name}")
    private lateinit var rootFolderName: String

    /**
     * To get a folder relative to the base folder -> [getBaseFolder]
     * @param relativePath the target path relative to the base folder
     */
    fun getFolder(relativePath: String): File {
        val file = getFile(relativePath)
        if (!file.isDirectory) {
            throw RuntimeException("Folder ${file.absolutePath} not found or its a file")
        }
        return file
    }

    fun getFile(relativePath: String): File {
        return File(getBaseFolder(), relativePath)
    }

    /**
     * Is returning the base path
     * Java project root in development and the folder of the jar file in production
     */
    fun getBaseFolder(): File {
        var path: String = PathLocationService::class.java.getProtectionDomain().getCodeSource().getLocation().getPath()
        path = path.replace("%20".toRegex(), " ") // replace whitespace placeholder
        if(rootFolderName.startsWith("\${")) {
            throw IllegalStateException("The required property root-folder.name was not set!")
        }
        return findFolder(File(path), listOf(".jar", rootFolderName))
    }

    /**
     * Tries recursive to find an parent folder which name ends with any suffix of the targetFolderSuffixes list
     */
    private fun findFolder(currentFile: File, targetFolderSuffixes: List<String>): File {
        if (targetFolderSuffixes.any { currentFile.name.endsWith(it) }) {
            return currentFile
        }
        return findFolder(currentFile.parentFile, targetFolderSuffixes)
    }
}