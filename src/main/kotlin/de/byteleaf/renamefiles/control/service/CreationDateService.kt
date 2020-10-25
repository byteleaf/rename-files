package de.byteleaf.renamefiles.control.service

import com.drew.imaging.ImageMetadataReader
import com.drew.metadata.Directory
import com.drew.metadata.exif.ExifIFD0Directory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.nio.file.Path
import java.util.*

@Service
class CreationDateService {

    @Autowired
    private lateinit var dateService: DateService

    /**
     * Tries to receive (or parse) the creation date of a file from this sources:
     * in this order:
     * 1) MetaData ExifIF TAG_DATETIME
     * 2) Try to parse date from filename (TODO)
     */
    fun getCreationDate(path: Path): Date? {
        var crDate: Date? = null
        val metadata = ImageMetadataReader.readMetadata(path.toFile())
        val directory: Directory? = metadata.getFirstDirectoryOfType(ExifIFD0Directory::class.java)
        if (null != directory) {
            crDate = directory.getDate(ExifIFD0Directory.TAG_DATETIME)
        }
        return crDate
    }

    fun getCreationDateAsString(path: Path, fileNameFormat: String): String? {
        val dateCreated = getCreationDate(path) ?: return null
        return dateService.formatDate(dateCreated, fileNameFormat)
    }
}