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
    fun getCreationDate(path: Path): OffsetDateTime {
        val metadata = ImageMetadataReader.readMetadata(path.toFile())
        val crDate = getCreationDateFromExif(metadata)
        val timeZoneOffset = getExifData<String>(metadata, ExifSubIFDDirectory::class.java, ExifSubIFDDirectory.TAG_TIME_ZONE, RenameStatus.TIMEZONE_NOT_FOUND_IN_EXIF)
        return crDate.toInstant().atOffset(ZoneOffset.UTC);
    }

    fun getCreationDateFromExif(metadata: Metadata): Date {
        var date = getExifData<Date>(metadata, ExifSubIFDDirectory::class.java, ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL, RenameStatus.CREATION_DATE_NOT_FOUND_IN_EXIF, true)
        if (date == null) {
            date = getExifData<Date>(metadata, ExifSubIFDDirectory::class.java, ExifIFD0Directory.TAG_DATETIME, RenameStatus.CREATION_DATE_NOT_FOUND_IN_EXIF, true)
        }
        if (date == null) {
            throw RenameStatusException(RenameStatus.CREATION_DATE_NOT_FOUND_IN_EXIF)
        }
        return date;
    }


    private fun <T> getExifData(metadata: Metadata, directoryType: Class<out ExifDirectoryBase>, tag: Int, errorStatus: RenameStatus, returnDate: Boolean = false): T? {
        if (metadata.getDirectoriesOfType(directoryType).count() == 0) {
            if (returnDate) {
                return metadata.getFirstDirectoryOfType(directoryType).getDate(tag) as T
            }
            return metadata.getFirstDirectoryOfType(directoryType).getString(tag) as T
        }
        return null
    }

    fun getCreationDateAsString(path: Path, fileNameFormat: String): String {
//        val dateCreated = getCreationDate(path) ?: return null
//        return dateService.formatDate(dateCreated, fileNameFormat)
        return ""
    }
}