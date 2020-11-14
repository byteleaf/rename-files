package de.byteleaf.renamefiles.control.service

import org.springframework.stereotype.Service
import java.io.File

@Service
class PathLocationService {

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

    private fun getBaseFolder(): File {
        return File(System.getProperty("user.dir"))
    }

    fun getRelativePath(file: File): String {
        return file.absolutePath.replace(getBaseFolder().absolutePath, "")
    }
}