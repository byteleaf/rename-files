package de.byteleaf.renamefiles.service

import com.drew.imaging.ImageMetadataReader
import com.drew.metadata.Directory
import com.drew.metadata.Metadata
import com.drew.metadata.exif.ExifSubIFDDirectory
import com.drew.metadata.file.FileSystemDirectory
import com.drew.metadata.mp4.Mp4Directory
import de.byteleaf.renamefiles.constant.FileType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.nio.file.Path
import java.util.*


@Service
class CreationDateService {

    data class MetaData(val directoryType: Class<out Directory>, val tag: Int)

    @Autowired
    private lateinit var dateService: DateService

    /**
     * Tries to receive (or parse) the creation date of the file MetaData ExifIF TAG_DATETIME
     */
    fun getCreationDateAsString(path: Path, fileNameFormat: String, fileType: FileType): String? {
        val metadata = ImageMetadataReader.readMetadata(path.toFile())
        val crDate = when (fileType) {
            FileType.JPG -> getCreationDateImage(metadata, fileNameFormat)
            FileType.MP4 -> getCreationDateMP4(metadata, fileNameFormat)
            FileType.HEIC -> getCreationDateImage(metadata, fileNameFormat)
        } ?: getFileModifiedDate(metadata, fileNameFormat)

        if (crDate != null) {
            val calendar = Calendar.getInstance()
            calendar.time = dateService.parseDate(crDate, fileNameFormat)
            // must be a mistake, if photo was taken before
            if (calendar.get(Calendar.YEAR) < 1950) {
                return getFileModifiedDate(metadata, fileNameFormat)
            }
        }
        return crDate
    }

    /**
     * If no timestamp was found, the last fallback is the FileSystemDirectory.TAG_FILE_MODIFIED_DATE
     */
    private fun getFileModifiedDate(metadata: Metadata, fileNameFormat: String): String? {
        val crDate = getExifData<Date>(metadata, listOf(MetaData(FileSystemDirectory::class.java, FileSystemDirectory.TAG_FILE_MODIFIED_DATE)))
        if (crDate != null) return dateService.formatDate(crDate, fileNameFormat)
        return null;
    }

    private fun getCreationDateImage(metadata: Metadata, fileNameFormat: String): String? {
        val crDate = getExifData<Date>(metadata, listOf(MetaData(ExifSubIFDDirectory::class.java, ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL)))
        if (crDate != null) return dateService.formatDate(crDate, fileNameFormat)
        return null
    }

    private fun getCreationDateMP4(metadata: Metadata, fileNameFormat: String): String? {
        val crDate = getExifData<Date>(metadata, listOf(MetaData(Mp4Directory::class.java, Mp4Directory.TAG_CREATION_TIME)))
        if (crDate != null) {
            return formatWithTimeZone(crDate, fileNameFormat, crDate.timezoneOffset * -1)
        }
        return null
    }

    private fun formatWithTimeZone(crDate: Date?, fileNameFormat: String, timeZoneOffsetMinutes: Int): String? {
        if (crDate != null) {
            val cal = Calendar.getInstance()
            cal.time = crDate
            cal.add(Calendar.MINUTE, timeZoneOffsetMinutes)
            return dateService.formatDate(cal.time, fileNameFormat)
        }
        return null
    }

    private fun <T> getExifData(metadata: Metadata, directories: List<MetaData>, returnDate: Boolean = true): T? {
        directories.forEach {
            val crDate = getExifData<T>(metadata, it.directoryType, it.tag, returnDate)
            if (crDate != null) {
                return crDate
            }
        }
        return null
    }

    private fun <T> getExifData(metadata: Metadata, directoryType: Class<out Directory>, tag: Int, returnDate: Boolean = false): T? {
        if (metadata.getDirectoriesOfType(directoryType).count() > 0) {
            if (returnDate) {
                return metadata.getFirstDirectoryOfType(directoryType).getDate(tag) as T
            }
            return metadata.getFirstDirectoryOfType(directoryType).getString(tag) as T
        }
        return null
    }
}