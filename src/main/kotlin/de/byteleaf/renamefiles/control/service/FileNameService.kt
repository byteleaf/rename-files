package de.byteleaf.renamefiles.control.service

import com.drew.imaging.ImageProcessingException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.IOException
import java.lang.Exception
import java.nio.file.Path
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

@Service
class FileNameService {

    @Autowired
    private lateinit var creationDateService: CreationDateService
    @Autowired
    private lateinit var dateService: DateService
    @Autowired
    private lateinit var fileTypeService: FileTypeService

    @Throws(IOException::class, ImageProcessingException::class)
    fun generateName(path: Path, fileNameFormat: String, fileNameSuffix: String): String {
        val dateCreated = creationDateService.getCreationDate(path)
        if(dateCreated != null) {
            val dateCreatedTs = dateService.formatDate(dateCreated, fileNameFormat)
            val fileEnding = fileTypeService.getFileEnding(path)
            val appendix = getAppendix(path, dateCreatedTs, fileNameSuffix,fileEnding)
            return "$dateCreatedTs$appendix$fileNameSuffix$fileEnding"
        }
        // TODO could not find creation Date -> List of files which could not be renamed!
        throw Exception("Could not find any information about the creation date")
    }

    /**
     * To check weather the file is already in the wanted pattern. If it is, a renaming is not necessary!
     */
    fun isRenameNecessary(path: Path, fileNameFormat: String, fileNameSuffix: String): Boolean {
        val fileName = path.fileName.toString()
        if((fileNameFormat.length + fileNameSuffix.length) != fileName.length || !fileName.endsWith(fileNameSuffix)) {
            return false
        }

        return true
    }

    fun getAppendix(path: Path, dateCreated: String, fileNameSuffix: String, fileEnding: String, counter: Int = 0): String {
        return "";
        // TODO implement
//        val counterString = if (counter > 0) "-$counter" else ""
//        val sibling = path.resolveSibling(dateCreated + counterString + fileEnding)
//        return if (sibling.toFile().exists()) {
//            getAppendix(dateCreated, fileEnding, path, counter + 1)
//        } else counterString
    }
}