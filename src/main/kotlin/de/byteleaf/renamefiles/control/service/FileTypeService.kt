package de.byteleaf.renamefiles.control.service

import de.byteleaf.renamefiles.constant.FileType
import org.springframework.stereotype.Service
import java.nio.file.Path

@Service
class FileTypeService {

    fun getBySuffix(suffix: String): FileType? {
        return FileType.values().find { it.suffix.equals(suffix) }
    }

    fun getFileType(path: Path): FileType? {
        val fileName = path.toFile().name
        if (fileName.contains(".")) {
            return getBySuffix(fileName.substring(fileName.lastIndexOf(".")))
        }
        return null
    }

    fun isFileTypeSupported(path: Path): Boolean {
        return getFileType(path) !== null
    }
}