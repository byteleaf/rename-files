package de.byteleaf.renamefiles.control.service

import com.drew.imaging.ImageProcessingException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.IOException
import java.nio.file.Path

@Service
class FileNameService {

    @Autowired
    private lateinit var creationDateService: CreationDateService

    @Autowired
    private lateinit var dateService: DateService

    @Autowired
    private lateinit var fileTypeService: FileTypeService

    @Throws(IOException::class, ImageProcessingException::class)
    fun generateName(path: Path, fileNameFormat: String, fileNameSuffix: String): String? {
        val dateCreated = creationDateService.getCreationDate(path) ?: return null
        val dateCreatedTs = dateService.formatDate(dateCreated, fileNameFormat)
        val fileEnding = fileTypeService.getFileEnding(path)
        val appendix = getAppendix(path, dateCreatedTs, fileNameSuffix, fileEnding)
        return concatFileName(dateCreatedTs, appendix, fileNameSuffix, fileEnding)
    }

    /**
     * To check weather a rename of a file is possible:
     * - file type must be supported
     * - creation date must be set in the file meta data
     */
    fun isRenamePossible(path: Path): Boolean = fileTypeService.isFileTypeSupported(path) && creationDateService.getCreationDate(path) != null

    fun getAppendix(path: Path, dateCreated: String, fileNameSuffix: String, fileEnding: String, counter: Int = 0): String {
        val counterString = if (counter > 0) "-$counter" else ""
        val sibling = path.resolveSibling(concatFileName(dateCreated, counterString, fileNameSuffix, fileEnding))
        return if (sibling.toFile().exists()) {
            getAppendix(path, dateCreated, fileNameSuffix, fileEnding, counter + 1)
        } else counterString
    }

    private fun concatFileName(dateCreated: String, appendix: String, fileNameSuffix: String, fileEnding: String) = "$dateCreated$appendix$fileNameSuffix$fileEnding"
}