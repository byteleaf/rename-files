package de.byteleaf.renamefiles.service

import com.drew.imaging.ImageProcessingException
import de.byteleaf.renamefiles.constant.RenameStatus
import de.byteleaf.renamefiles.model.DateTimeAdjustment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.IOException
import java.nio.file.Path
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Service
class FileNameService {

    @Autowired
    private lateinit var creationDateService: CreationDateService
    @Autowired
    private lateinit var fileTypeService: FileTypeService
    @Autowired
    private lateinit var dateService: DateService

    /**
     * Generates a valid, not used file name
     * @sample 2020-02-11-3_suffix.jpg
     */
    @Throws(IOException::class, ImageProcessingException::class)
    fun generateName(path: Path, fileNameFormat: String, fileNameSuffix: String): String {
        val fileEnding = fileTypeService.getFileType(path)!!.suffix
        val dateCreatedTs = creationDateService.getCreationDateAsString(path, fileNameFormat, fileTypeService.getFileType(path)!!)!!
        val appendix = getAppendix(path, dateCreatedTs, fileNameSuffix, fileEnding)
        return concatFileName(dateCreatedTs, appendix, fileNameSuffix, fileEnding)
    }

    @Throws(IOException::class, ImageProcessingException::class)
    fun generateNameWithDateTimeAdjustment(path: Path, fileNameFormat: String, fileNameSuffix: String, dateTimeAdjustment: DateTimeAdjustment): String {
        val fileEnding = fileTypeService.getFileType(path)!!.suffix
        val dateCreatedTs = creationDateService.getCreationDateAsString(path, fileNameFormat, fileTypeService.getFileType(path)!!)!!

        val formatter = DateTimeFormatter.ofPattern(fileNameFormat)
        var dateAdjusted = LocalDateTime.parse(dateCreatedTs, formatter)
        if(dateTimeAdjustment.hoursAdd != 0) {
            dateAdjusted = dateAdjusted.plusHours(dateTimeAdjustment.hoursAdd.toLong())
        }

        val dateAdjustedString = dateAdjusted.format(formatter)
        return "$dateAdjustedString$fileNameSuffix.$fileEnding"
    }

    /**
     * If the file is still existing the next free appendix will be returned.
     * This happens if multiple files have been created in the same second
     * @sample 2020.jpg is still existing, so this method will return "-1", which means the file with
     * appendix 2020-1.jpg is still not existing
     */
    fun getAppendix(path: Path, dateCreated: String, fileNameSuffix: String, fileEnding: String, counter: Int = 0): String {
        val counterString = if (counter > 0) "-$counter" else ""
        val sibling = path.resolveSibling(concatFileName(dateCreated, counterString, fileNameSuffix, fileEnding))
        return if (sibling.toFile().exists()) {
            getAppendix(path, dateCreated, fileNameSuffix, fileEnding, counter + 1)
        } else counterString
    }

    private fun concatFileName(dateCreated: String, appendix: String, fileNameSuffix: String, fileEnding: String) = "$dateCreated$appendix$fileNameSuffix.$fileEnding"


    /**
     * To get the status if a renamed is possible and necessary
     * @return the [RenameStatus] if [RenameStatus.RENAMED] means a renamed would be possible!
     */
    fun shouldRename(path: Path, fileNameFormat: String, fileNameSuffix: String): RenameStatus {
        if (!fileTypeService.isFileTypeSupported(path)) return RenameStatus.FILE_TYPE_NOT_SUPPORTED
        if (creationDateService.getCreationDateAsString(path, fileNameFormat, fileTypeService.getFileType(path)!!) == null) return RenameStatus.CREATION_DATE_NOT_FOUND_IN_EXIF
        if (!isRenameNecessary(path, fileNameFormat, fileNameSuffix)) return RenameStatus.RENAME_NOT_NECESSARY
        return RenameStatus.RENAMED
    }

    /**
     * To check weather a file is still in the correct name format
     */
    fun isRenameNecessary(path: Path, fileNameFormat: String, fileNameSuffix: String): Boolean {
        val fileName = path.fileName.toFile().nameWithoutExtension
        val creationDate = creationDateService.getCreationDateAsString(path, fileNameFormat, fileTypeService.getFileType(path)!!) ?: ""
        if (fileName.length < (fileNameSuffix + creationDate).length) return true
        if (!fileName.startsWith(creationDate) || !fileName.endsWith(fileNameSuffix)) return true
        val appendix = fileName.substring(creationDate.length, fileName.length - fileNameSuffix.length)
        if (appendix == "" || """-\d+""".toRegex().containsMatchIn(appendix)) {
            val newFileName = "$creationDate$appendix$fileNameSuffix"
            if (newFileName.equals(fileName)) return false
        }
        return true
    }
}