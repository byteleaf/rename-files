package de.byteleaf.renamefiles.control.service

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
        val fileName = path.toFile().name
        if (fileName.contains(".")) {
            return getBySuffix(fileName.substring(fileName.lastIndexOf(".")+1))
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