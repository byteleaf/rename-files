package de.byteleaf.renamefiles.control.service

import com.drew.imaging.ImageProcessingException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.IOException
import java.nio.file.Path
import java.text.SimpleDateFormat
import java.util.*

@Service
class FileNameService {

    @Autowired
    private lateinit var creationDateService: CreationDateService

    @Throws(IOException::class, ImageProcessingException::class)
    fun generateName(path: Path, displayUnrenamed: Boolean, nameFormat: String): String {
        val dateCreated = creationDateService.getCreationDate(path)
//
//        val fileEnding = getFileEnding(path)
//        return dateCreated + getAppendix(dateCreated, fileEnding, path, 0) + getFileEnding(path)
        return ""
    }

    /**
     * To check weather the file is already in the wanted pattern
     */
    fun isNameCorrect(): Boolean {
        return false
    }

    fun getAppendix(dateCreated: String, fileEnding: String, path: Path, counter: Int): String {
        val counterString = if (counter > 0) "-$counter" else ""
        val sibling = path.resolveSibling(dateCreated + counterString + fileEnding)
        return if (sibling.toFile().exists()) {
            getAppendix(dateCreated, fileEnding, path, counter + 1)
        } else counterString
    }

    fun formatDate(date: Date?): String {
        val df = SimpleDateFormat("yyyy-MM-dd HH-mm-ss")
        return df.format(date)
    }
}