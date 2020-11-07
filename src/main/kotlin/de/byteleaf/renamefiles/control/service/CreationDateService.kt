package de.byteleaf.renamefiles.control.service

import com.drew.imaging.ImageMetadataReader
import com.drew.metadata.Metadata
import com.drew.metadata.exif.ExifDirectoryBase
import com.drew.metadata.exif.ExifIFD0Directory
import com.drew.metadata.exif.ExifSubIFDDirectory
import de.byteleaf.renamefiles.constant.RenameStatus
import de.byteleaf.renamefiles.control.error.exception.RenameStatusException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.nio.file.Path
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.*

@Service
class CreationDateService {

    @Autowired
    private lateinit var dateService: DateService


    /**
     * Tries to receive (or parse) the creation date of the file MetaData ExifIF TAG_DATETIME
     */
    fun getCreationDateAsString(path: Path, fileNameFormat: String): String? {
        val metadata = ImageMetadataReader.readMetadata(path.toFile())
        val crDate = getExifData<Date>(metadata, ExifSubIFDDirectory::class.java, ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL, true)
        if(crDate != null) {
            return dateService.formatDate(crDate, fileNameFormat)
        }
        return null
    }

    private fun <T> getExifData(metadata: Metadata, directoryType: Class<out ExifDirectoryBase>, tag: Int, returnDate: Boolean = false): T? {
        if (metadata.getDirectoriesOfType(directoryType).count() > 0) {
            if (returnDate) {
                return metadata.getFirstDirectoryOfType(directoryType).getDate(tag) as T
            }
            return metadata.getFirstDirectoryOfType(directoryType).getString(tag) as T
        }
        return null
    }
}