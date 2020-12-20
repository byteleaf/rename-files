package de.byteleaf.renamefiles.service

import de.byteleaf.renamefiles.constant.FileType
import org.springframework.stereotype.Service
import java.nio.file.Path

@Service
class FileTypeService {

    private fun getBySuffix(suffix: String): FileType? {
        return FileType.values().find { it.suffix.equals(suffix) }
    }

    /**
     * To get the type of file
     * @param path the path of the file
     * @return the [FileType] or null if the type is not valid
     */
    fun getFileType(path: Path): FileType? {
        val extension = path.toFile().extension
        if (extension.isNotEmpty()) {
            return getBySuffix(extension.toLowerCase())
        }
        return null
    }

    /**
     * To check if the file type is supported by the application
     */
    fun isFileTypeSupported(path: Path): Boolean {
        return getFileType(path) !== null
    }
}